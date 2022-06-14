package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.dto.music.MusicCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.music.MusicDto;
import cn.fluorescent.fluorescentmusic.dto.music.MusicUpdateRequest;
import cn.fluorescent.fluorescentmusic.entity.Music;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public interface MusicService extends IService<Music> {

    MusicDto create(MusicCreateRequest musicCreateRequest);

    MusicDto update(String id, MusicUpdateRequest musicUpdateRequest);

    void publish(String id);

    void close(String id);

}
