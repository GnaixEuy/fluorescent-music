package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.controller.vo.ResponseResult;
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
@RequestMapping(value = {"/hello"})
public class HelloController {

    @GetMapping(value = {""})
    public ResponseResult<String> test() {
        return ResponseResult.success("开始项目吧! 祝一切顺利");
    }

}
