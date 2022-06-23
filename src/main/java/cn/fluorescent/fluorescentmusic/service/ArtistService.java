package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.dto.file.FileDto;
import cn.fluorescent.fluorescentmusic.dto.music.MusicCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.music.MusicDto;
import cn.fluorescent.fluorescentmusic.dto.music.MusicUpdateRequest;
import cn.fluorescent.fluorescentmusic.entity.Music;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ArtistService extends IService<Music> {
    public abstract MusicDto putMusic(MusicCreateRequest musicCreateRequest);

    public abstract MusicDto updateMusic(String id,MusicUpdateRequest musicUpdateRequest);
}
