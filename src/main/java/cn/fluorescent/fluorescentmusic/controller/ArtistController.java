package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.music.MusicCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.music.MusicUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.File;
import cn.fluorescent.fluorescentmusic.entity.Music;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.FileMapper;
import cn.fluorescent.fluorescentmusic.mapper.MusicMapper;
import cn.fluorescent.fluorescentmusic.service.ArtistService;
import cn.fluorescent.fluorescentmusic.service.FileService;
import cn.fluorescent.fluorescentmusic.service.MusicService;
import cn.fluorescent.fluorescentmusic.vo.MusicVo;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import cn.fluorescent.fluorescentmusic.vo.file.FileVo;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private ArtistService artistService;

    private MusicMapper musicMapper;

    private MusicService musicService;

    private FileService fileService;

    private FileMapper fileMapper;

    @Value("${tencent.cos.base-url}")
    private String BASE_URL;

    @PostMapping(value = {"/{id}"})
    public ResponseResult<String> update(@PathVariable String id, @Validated @RequestBody MusicUpdateRequest musicUpdateRequest){
        Music music = this.artistService.getById(id);
        if (ObjectUtil.isNull(music)){
            throw new BizException(ExceptionType.MUSIC_NOT_FOUND);
        }
        music.setName(musicUpdateRequest.getName());
        String newDescription = musicUpdateRequest.getDescription();
        if(StrUtil.isNotEmpty(newDescription)){
            music.setDescription(newDescription);
        }
        boolean success = this.musicService.updateById(music);
        if(!success){
            throw new BizException(ExceptionType.MUSIC_UPDATE_ERROR);
        }
        return ResponseResult.success("更新成功！");
    }

    @PostMapping(value = {""})
    public ResponseResult<MusicVo> insert(@Validated @RequestBody MusicCreateRequest musicCreateRequest){
        Music music = this.musicMapper.toEntity(musicCreateRequest);
        boolean result = this.musicService.save(music);
        if(!result || ObjectUtil.isAllEmpty(music)){
            throw new BizException(ExceptionType.MUSIC_INSERT_ERROR);
        }
        music = this.musicService.getOne(
                Wrappers.<Music>lambdaQuery()
                        .eq(Music::getFileId, music.getFileId()));
        MusicVo musicVo = new MusicVo();
        File byId = this.fileService.getById(music.getFileId());
        FileVo fileVo = this.fileMapper.toVo(byId);
        this.music2MusicVo(music,musicVo,byId,fileVo);
        return ResponseResult.success(musicVo);
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
}
