package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.dao.MusicDao;
import cn.fluorescent.fluorescentmusic.dto.music.MusicCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.music.MusicDto;
import cn.fluorescent.fluorescentmusic.dto.music.MusicUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.Music;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.MusicMapper;
import cn.fluorescent.fluorescentmusic.service.ArtistService;
import cn.fluorescent.fluorescentmusic.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl extends ServiceImpl<MusicDao,Music> implements ArtistService {
    private MusicService musicService;

    private MusicDao musicDao;

    private MusicMapper musicMapper;


    @Override
    public MusicDto putMusic(MusicCreateRequest musicCreateRequest) {
        try {
            MusicDto musicDto = musicService.create(musicCreateRequest);
            return musicDto;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public MusicDto updateMusic(String id, MusicUpdateRequest musicUpdateRequest) {

        Music oldMusic = this.musicDao.selectById(id);
        Music newMusic = this.musicMapper.updateEntity(oldMusic, this.musicMapper.toDto(musicUpdateRequest));
        final int result = this.musicDao.updateById(newMusic);
        if (result == 1) {
            final Music resultMusic = this.musicDao.selectById(id);
            return this.musicMapper.toDto(resultMusic);
        }
        throw new BizException(ExceptionType.MUSIC_UPDATE_ERROR);
    }
}
