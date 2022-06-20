package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.PlayList;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.PlayListService;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/19
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@Api(tags = {"歌单接口"})
@RequestMapping(value = {"/playlist"})
public class PlayListController {
    private PlayListService playListService;

    @PostMapping(value = {"/{id}/{name}"})
    @ApiOperation(value = "创建音乐歌单接口，传入创建者id和歌单名")
    public ResponseResult<String> create(@PathVariable String id, @PathVariable String name) {
        PlayList playList = new PlayList(name);
        boolean sava = this.playListService.sava(id, playList);
        if (!sava) {
            throw new BizException(ExceptionType.PLAYLIST_CREATE_ERROR);
        }
        return ResponseResult.success("歌单创建成功");
    }

    @DeleteMapping(value = {"/{id}"})
    @ApiOperation(value = "删除歌单接口，传入歌单id")
    @Transactional
    public ResponseResult<String> delete(@PathVariable String id) {
        boolean clear = this.playListService.clear(id);
        if (clear) {
            boolean b = this.playListService.removeById(id);
            if (!b) {
                throw new BizException(ExceptionType.PLAYLIST_DELETE_ERROR);
            }
        }
        return ResponseResult.success("歌单删除成功");
    }

    @PutMapping(value = {"/add/{id}/{musicId}"})
    @ApiOperation(value = "传入歌单id和歌曲id，给歌单添加音乐")
    public ResponseResult<String> add(@PathVariable @NotBlank String id, @PathVariable @NotBlank String musicId) {
        this.playListService.associate(id, musicId);
        return ResponseResult.success("添加成功");
    }

    @PutMapping(value = {"/remove/{id}/{musicId}"})
    @ApiOperation(value = "传入歌单id和歌曲id，给歌单添加音乐")
    public ResponseResult<String> remove(@PathVariable @NotBlank String id, @PathVariable @NotBlank String musicId) {
        this.playListService.removeMusic(id, musicId);
        return ResponseResult.success("歌单音乐移除成功");
    }

    @GetMapping(value = {"/"})
    @ApiOperation(value = "获取所有歌单")
    public ResponseResult<List<PlayList>> list() {
        return ResponseResult.success(this.playListService.list());
    }

    @GetMapping(value = {"/{id}"})
    @ApiOperation(value = "通过id获取歌单信息")
    public ResponseResult<List<PlayList>> listById(@PathVariable String id) {
        return ResponseResult.success(this.playListService.list(Wrappers
                .<PlayList>lambdaQuery()
                .eq(PlayList::getId, id)));
    }


    @GetMapping(value = {"/search"})
    @ApiOperation(value = "歌单分页接口，获取指定条目的歌单")
    public ResponseResult<Page<PlayList>> search(Page<PlayList> page) {
        return ResponseResult.success(this.playListService.page(page));
    }

    @GetMapping(value = {"/user/{id}"})
    @ApiOperation(value = "获取指定用户的歌单列表，传入用户的id")
    public ResponseResult<List<PlayList>> userPlayList(@PathVariable String id) {
        List<PlayList> list = this.playListService
                .listByMap(new HashMap<String, Object>() {{
                    put("creator_id", id);
                }});
        return ResponseResult.success(list);
    }

    @Autowired
    public void setPlayListService(PlayListService playListService) {
        this.playListService = playListService;
    }

}
