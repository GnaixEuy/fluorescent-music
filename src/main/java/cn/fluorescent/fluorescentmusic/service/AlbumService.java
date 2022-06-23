package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.entity.Album;
import cn.fluorescent.fluorescentmusic.vo.Album.AlbumVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AlbumService extends IService<Album> {
    AlbumVo searchAlbumInfoById(String id);

    boolean clear(String id);

    boolean sava(String id, Album album);

    boolean associate(String id,String musicId,String artistId);

    boolean removeMusic(String id,String musicId);

    boolean updateAlbumById(Album album);
}
