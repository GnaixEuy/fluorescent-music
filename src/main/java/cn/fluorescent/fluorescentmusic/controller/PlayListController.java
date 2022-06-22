package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.PlayListCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.PlayListUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmus.PlayListStatus;
import cn.fluorescent.fluorescentmusic.entity.PlayList;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.PlayListService;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = {"playListCache"})
    public ResponseResult<String> create(@PathVariable @NotBlank String id,
                                         @PathVariable @NotBlank String name,
                                         @RequestBody PlayListCreateRequest playListCreateRequest) {
        PlayList playList = new PlayList(name);
        playList.setDescription(playListCreateRequest.getDescription());
        playList.setCoverUrl(playListCreateRequest.getCoverUrl());
        playList.setStatus(statusTranslator(Integer.valueOf(playListCreateRequest.getStatus())));
        boolean sava = this.playListService.sava(id, playList);
        if (!sava) {
            throw new BizException(ExceptionType.PLAYLIST_CREATE_ERROR);
        }
        return ResponseResult.success("歌单创建成功");
    }

    @DeleteMapping(value = {"/{id}"})
    @ApiOperation(value = "删除歌单接口，传入歌单id")
    @Transactional
    @CacheEvict(value = {"playListCache"})
    public ResponseResult<String> delete(@PathVariable String id) {
        System.out.println(id);
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
    @CacheEvict(value = {"playListCache"})
    public ResponseResult<String> add(@PathVariable @NotBlank String id,
                                      @PathVariable @NotBlank String musicId) {
        try {
            this.playListService.associate(id, musicId);
        } catch (BizException e) {
            throw new BizException(ExceptionType.PLAYLIST_UPDATE_ERROR);
        }
        return ResponseResult.success("添加成功");
    }

    @PutMapping(value = {"/{id}"})
    @ApiOperation(value = "更新歌单描述接口")
    @CacheEvict(value = {"playListCache"})
    public ResponseResult<String> update(@PathVariable @NotBlank String id,
                                         @RequestBody PlayListUpdateRequest playListUpdateRequest) {
        PlayList byId = this.playListService.getById(id);
        if (byId == null) {
            throw new BizException(ExceptionType.PLAYLIST_NOT_FOUND);
        }
        if (StrUtil.isNotBlank(playListUpdateRequest.getName())) {
            byId.setName(playListUpdateRequest.getName());
        }
        if (StrUtil.isNotBlank(playListUpdateRequest.getDescription())) {
            byId.setDescription(playListUpdateRequest.getDescription());
        }
        byId.setStatus(this.statusTranslator(Integer.parseInt(playListUpdateRequest.getStatus())));
        boolean updateResult = this.playListService.updatePlayListById(byId);
        if (!updateResult) {
            throw new BizException(ExceptionType.PLAYLIST_UPDATE_ERROR);
        }
        return ResponseResult.success("歌单描述更新成功");
    }


    @PutMapping(value = {"/remove/{id}/{musicId}"})
    @ApiOperation(value = "传入歌单id和歌曲id，给歌单移除音乐")
    @CacheEvict(value = {"playListCache"})
    public ResponseResult<String> remove(@PathVariable @NotBlank String id,
                                         @PathVariable @NotBlank String musicId) {
        try {
            this.playListService.removeMusic(id, musicId);
        } catch (BizException e) {
            throw new BizException(ExceptionType.PLAYLIST_UPDATE_ERROR);
        }
        return ResponseResult.success("歌单音乐移除成功");
    }

    @GetMapping(value = {"/"})
    @ApiOperation(value = "获取所有歌单")
    @Cacheable(value = {"playListCache"})
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

    private PlayListStatus statusTranslator(Integer status) {
        PlayListStatus playListStatus;
        switch (status) {
            case 2:
                playListStatus = PlayListStatus.PUBLISHED;
                break;
            case 3:
                playListStatus = PlayListStatus.CLOSED;
                break;
            default:
                playListStatus = PlayListStatus.DRAFT;
        }
        return playListStatus;
    }

    @Autowired
    public void setPlayListService(PlayListService playListService) {
        this.playListService = playListService;
    }

}
