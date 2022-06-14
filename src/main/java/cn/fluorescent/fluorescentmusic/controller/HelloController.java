package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.service.StorageService;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： shopping-system </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@Api(tags = {"欢迎开始"})
@RequestMapping(value = {"/hello"})
public class HelloController {

    private StorageService cos;

    @GetMapping(value = {""})
    @ApiOperation(value = "开始项目，基本通用vo格式")
    public ResponseResult<String> test() {
        return ResponseResult.success("开始项目吧! 祝一切顺利");
    }

    @GetMapping(value = {"/testStorageService"})
    @ApiOperation(value = "查看腾讯云对象存储配置信息，不要使用")
    public ResponseResult<StorageService> testStorageService() {
        return ResponseResult.success(this.cos);
    }

    @Autowired
    public void setCos(StorageService cos) {
        this.cos = cos;
    }
}
