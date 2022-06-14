package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.dao.MusicDao;
import cn.fluorescent.fluorescentmusic.dto.music.MusicCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.music.MusicDto;
import cn.fluorescent.fluorescentmusic.dto.music.MusicUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmus.MusicStatus;
import cn.fluorescent.fluorescentmusic.entity.Music;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.MusicMapper;
import cn.fluorescent.fluorescentmusic.service.MusicService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy, @Autowired})
public class MusicServiceImpl extends ServiceImpl<MusicDao, Music> implements MusicService {

    private final MusicDao musicDao;
    private final MusicMapper musicMapper;

    @Override
    public MusicDto create(MusicCreateRequest musicCreateRequest) {
        log.info("开始保存音乐业务");
        Music music = this.musicMapper.toEntity(this.musicMapper.toDto(musicCreateRequest));
        music.setStatus(MusicStatus.DRAFT);
        log.info("填充状态完成");
        if (this.musicDao.insert(music) == 1) {
            log.info("保存完成");
            final Music resultMusic = this.musicDao.selectOne(Wrappers.<Music>lambdaQuery().eq(Music::getName, music.getName()).eq(Music::getDescription, music.getDescription()));
            log.info("转换完成");
            return this.musicMapper.toDto(resultMusic);
        } else {
            throw new BizException(ExceptionType.MUSIC_INSERT_ERROR);
        }
    }

    @Override
    public MusicDto update(String id, MusicUpdateRequest musicUpdateRequest) {
        final Music oldMusic = this.getMusic(id);
        final Music music = this.musicMapper.updateEntity(oldMusic, this.musicMapper.toDto(musicUpdateRequest));
        final int result = this.musicDao.updateById(music);
        if (result == 1) {
            final Music resultMusic = this.musicDao.selectById(id);
            return this.musicMapper.toDto(resultMusic);
        }
        throw new BizException(ExceptionType.MUSIC_UPDATE_ERROR);
    }

    @Override
    public void publish(String id) {
        this.changeStatus(id, MusicStatus.PUBLISH);
    }

    @Override
    public void close(String id) {
        this.changeStatus(id, MusicStatus.CLOSED);
    }

    private void changeStatus(String id, MusicStatus musicStatus) {
        final Music music = this.getMusic(id);
        music.setStatus(musicStatus);
        final int result = this.musicDao.updateById(music);
        if (result != 1) {
            throw new BizException(ExceptionType.MUSIC_UPDATE_ERROR);
        }
    }


    private Music getMusic(String id) {
        final Music music = this.musicDao.selectById(id);
        if (music == null) {
            throw new BizException(ExceptionType.MUSIC_NOT_FOUND);
        }
        return music;
    }
}
