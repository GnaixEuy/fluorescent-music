package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.user.WeChatUserCreateRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.WXSessionModel;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.UserService;
import cn.fluorescent.fluorescentmusic.utils.HttpClientUtil;
import cn.fluorescent.fluorescentmusic.utils.JsonUtils;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/13
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */

@RestController
@RequestMapping("/weixin")
@Api(tags = {"微信登录接口，开发中基本完成，但为经过测试，别用！"})
public class WeChatController {

    @Value("${weixin.mp.app.id}")
    private String appid;
    @Value("${weixin.mp.app.secret}")
    private String secret;

    private UserService userService;

    private WxMpService wxMpService;

    @GetMapping("/auth_url")
    @ApiOperation(value = "微信扫码登录接口，默认网址配置还存在问题，但是可以获取连接，可以对照微信开发文档先看")
    public String getAuthUrl(@PathParam("redirectUrl") String redirectUrl) {
        return wxMpService.getOAuth2Service().buildAuthorizationUrl(
                redirectUrl,
                WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                null
        );
    }

    @PostMapping(value = {"/wxLogin"})
    @ApiOperation(value = "微信登录接口，返回40401002为后端用户不存在，请发送用户信息进行注册")
    public ResponseResult<String> wxLogin(String code, @RequestBody WeChatUserCreateRequest weChatUserCreateRequest) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", appid);
        param.put("secret", secret);
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        String wxResult = HttpClientUtil.doGet(url, param);
        WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
        assert model != null;
        String openid = model.getOpenid();
        try {
            return ResponseResult.success(this.userService.createTokenByOpenId(openid));
        } catch (BizException bizException) {
            boolean result = this.userService.registeredUserWithOpenId(openid, weChatUserCreateRequest);
            if (!result) {
                throw new BizException(ExceptionType.USER_INSERT_ERROR);
            }
            return ResponseResult.success(this.userService.createTokenByOpenId(openid));
        }
    }


    @Autowired
    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}

