package cn.fluorescent.fluorescentmusic.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/13
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RequiredArgsConstructor(onConstructor_ = {@Lazy, @Autowired})
@RestController
@RequestMapping("/weixin")
@Api(tags = {"微信登录接口，开发中基本完成，但为经过测试，别用！"})
public class WeChatController {

    private WxMpService wxMpService;

    @GetMapping("/auth_url")
    @ApiOperation(value = "微信一件登录接口，默认网址配置还存在问题，但是可以获取连接，可以对照微信开发文档先看")
    public String getAuthUrl(@PathParam("redirectUrl") String redirectUrl) {
        return wxMpService.getOAuth2Service().buildAuthorizationUrl(
                redirectUrl,
                WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                null
        );
    }


    @Autowired
    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

}

