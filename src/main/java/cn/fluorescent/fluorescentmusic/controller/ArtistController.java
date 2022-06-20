package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.entity.Artist;
import cn.fluorescent.fluorescentmusic.service.ArtistService;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/21
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@Api(tags = "音乐人接口")
@RequestMapping(value = {"/artist"})
public class ArtistController {
    private ArtistService artistService;

    @GetMapping(value = {"/"})
    @ApiOperation(value = "获取所有音乐人信息接口")
    public ResponseResult<List<Artist>> list() {
        return ResponseResult.success(this.artistService.list());
    }

    @GetMapping(value = {"/search"})
    @ApiOperation(value = "获取部分音乐人信息接口（分页）")
    public ResponseResult<Page<Artist>> search(Page<Artist> page) {
        return ResponseResult.success(this.artistService.page(page));
    }


    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }
}
