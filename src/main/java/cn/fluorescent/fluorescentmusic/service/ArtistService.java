package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.entity.Artist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author gnaixeuy
 * @description 针对表【artist(歌手表)】的数据库操作Service
 * @createDate 2022-06-20 22:27:00
 */
public interface ArtistService extends IService<Artist> {

    boolean savaArtist(Artist artist);

}
