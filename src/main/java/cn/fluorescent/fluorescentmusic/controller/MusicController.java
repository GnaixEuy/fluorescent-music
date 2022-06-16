package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.music.MusicCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.music.MusicUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.File;
import cn.fluorescent.fluorescentmusic.entity.Music;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.FileMapper;
import cn.fluorescent.fluorescentmusic.mapper.MusicMapper;
import cn.fluorescent.fluorescentmusic.service.FileService;
import cn.fluorescent.fluorescentmusic.service.MusicService;
import cn.fluorescent.fluorescentmusic.vo.MusicVo;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import cn.fluorescent.fluorescentmusic.vo.file.FileVo;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/16
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@Api(value = "音乐接口")
@RequestMapping(value = {"/music"})
public class MusicController {

    private MusicService musicService;

    private MusicMapper musicMapper;

    private FileService fileService;

    private FileMapper fileMapper;

    @Value("${tencent.cos.base-url}")
    private String BASE_URL;

    @PostMapping(value = {""})
    @ApiOperation(value = "增加音乐接口")
    @Transactional
    public ResponseResult<MusicVo> insert(@RequestBody MusicCreateRequest musicCreateRequest) {
        Music music = this.musicMapper.toEntity(musicCreateRequest);
        boolean result = this.musicService.save(music);
        if (!result || ObjectUtil.isAllEmpty(music)) {
            throw new BizException(ExceptionType.MUSIC_INSERT_ERROR);
        }
        //从持久层中重新提取信息，保证各项信息完整性
        music = this.musicService.getOne(
                Wrappers.<Music>lambdaQuery()
                        .eq(Music::getFileId, music.getFileId()));
        MusicVo musicVo = new MusicVo();
        File byId = this.fileService.getById(music.getFileId());
        FileVo fileVo = this.fileMapper.toVo(byId);
        this.music2MusicVo(music, musicVo, byId, fileVo);
        return ResponseResult.success(musicVo);
    }


    @GetMapping(value = {"/{id}"})
    @ApiOperation(value = "通过id获取音乐详细信息")
    public ResponseResult<MusicVo> musicById(@PathVariable String id) {
        Music music = this.musicService.getById(id);
        if (ObjectUtil.isNull(music)) {
            throw new BizException(ExceptionType.MUSIC_NOT_FOUND);
        }
        MusicVo musicVo = new MusicVo();
        File byId = this.fileService.getById(music.getFileId());
        FileVo fileVo = this.fileMapper.toVo(byId);
        this.music2MusicVo(music, musicVo, byId, fileVo);
        return ResponseResult.success(musicVo);
    }

    @GetMapping(value = {""})
    @ApiOperation(value = "获取所有音乐接口")
    public ResponseResult<List<MusicVo>> list() {
        List<Music> list = this.musicService.list();
        return musicList2MusicVoList(list);
    }

    @GetMapping(value = {"/nameTip/{name}"})
    @ApiOperation(value = "搜索歌曲时返回名称提示")
    public ResponseResult<List<String>> nameSuggests(@PathVariable String name) {
        return ResponseResult.success(this.musicService.nameSearchTip(name));
    }

    @GetMapping(value = {"/getByName/{name}"})
    @ApiOperation(value = "根据音乐名来获取音乐信息")
    public ResponseResult<List<MusicVo>> getByName(@PathVariable String name) {
        List<Music> list = this.musicService.list(Wrappers.<Music>lambdaQuery().eq(Music::getName, name));
        return musicList2MusicVoList(list);
    }

    @DeleteMapping(value = {"/{id}"})
    @ApiOperation(value = "根据id删除对应音乐")
    public ResponseResult<String> delete(@PathVariable String id) {
        boolean b = this.musicService.removeById(id);
        if (!b) {
            throw new BizException(ExceptionType.MUSIC_NOT_FOUND);
        }
        return ResponseResult.success("删除成功");
    }

    @PutMapping(value = {"/{id}"})
    @ApiOperation(value = "根据id修改音乐信息状态等")
    public ResponseResult<String> update(@RequestBody MusicUpdateRequest musicUpdateRequest, @PathVariable String id) {
        System.out.println(musicUpdateRequest);
        Music byId = this.musicService.getById(id);
        if (ObjectUtil.isNull(byId)) {
            throw new BizException(ExceptionType.MUSIC_NOT_FOUND);
        }
        byId.setName(musicUpdateRequest.getName());
        String newDescription = musicUpdateRequest.getDescription();
        if (StrUtil.isNotEmpty(newDescription)) {
            byId.setDescription(newDescription);
        }

        byId.setStatus(musicUpdateRequest.getMusicStatus());
        boolean success = this.musicService.updateById(byId);
        if (!success) {
            throw new BizException(ExceptionType.MUSIC_UPDATE_ERROR);
        }

        return ResponseResult.success("音乐信息更新成功!");

    }


    private void music2MusicVo(Music music, MusicVo musicVo, File byId, FileVo fileVo) {
        fileVo.setUri(this.BASE_URL + "/" + byId.getKey());
        fileVo.setStatus(byId.getFileStatus());
        musicVo.setMusicFile(fileVo);
        musicVo.setImageUrl(music.getImageUrl());
        musicVo.setCreatedTime(music.getCreatedTime());
        musicVo.setUpdatedTime(music.getUpdatedTime());
        musicVo.setStatus(music.getStatus());
        musicVo.setId(music.getId());
        musicVo.setName(music.getName());
        musicVo.setImageUrl(music.getImageUrl());
        musicVo.setDescription(music.getDescription());
    }

    @NotNull
    private ResponseResult<List<MusicVo>> musicList2MusicVoList(List<Music> list) {
        List<MusicVo> resultList = new ArrayList<>(list.size());
        MusicVo musicVo;
        File byId;
        FileVo fileVo;
        for (Music music : list) {
            musicVo = new MusicVo();
            byId = this.fileService.getById(music.getFileId());
            fileVo = this.fileMapper.toVo(byId);
            this.music2MusicVo(music, musicVo, byId, fileVo);
            resultList.add(musicVo);
        }
        list.clear();
        return ResponseResult.success(resultList);
    }


    @Autowired
    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }

    @Autowired
    public void setMusicMapper(MusicMapper musicMapper) {
        this.musicMapper = musicMapper;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setFileMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
}
