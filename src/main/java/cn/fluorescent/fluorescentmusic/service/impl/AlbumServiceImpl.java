package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.dao.AlbumArtistDao;
import cn.fluorescent.fluorescentmusic.dao.AlbumDao;
import cn.fluorescent.fluorescentmusic.dao.AlbumMusicDao;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.*;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.AlbumService;
import cn.fluorescent.fluorescentmusic.service.ArtistService;
import cn.fluorescent.fluorescentmusic.vo.Album.AlbumVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumDao, Album> implements AlbumService {

    private AlbumDao albumDao;

    private ArtistService artistService;

    private AlbumMusicDao albumMusicDao;

    private AlbumArtistDao albumArtistDao;

    @Override
    public AlbumVo searchAlbumInfoById(String id) {
        Album album = albumDao.findAlbumInfoById(id);
        String artist_name = albumDao.findAlbumArtistNameById(id);

        AlbumVo albumVo = new AlbumVo();
        albumVo.setId(id);
        albumVo.setName(album.getName());
        albumVo.setDescription(album.getDescription());
        albumVo.setAlbum_url(album.getAlbum_url());
        albumVo.setCreator(artist_name);

        System.out.println(albumVo.getName());
        System.out.println(albumVo.getAlbum_url());

        return albumVo;
    }

    @Override
    public boolean clear(String id) {
        int delete = this.albumArtistDao.delete(Wrappers
                .<AlbumArtist>lambdaQuery()
                .eq(AlbumArtist::getAlbum_id, id));
        if (delete == 0) {
            System.out.println("1有问题");
            throw new BizException(ExceptionType.ARTIST_DELETE_ERROR); //TODO 后面定义album_artist删除失败时返回的错误状态
        }
        delete = this.albumMusicDao.delete(Wrappers
                .<AlbumMusic>lambdaQuery()
                .eq(AlbumMusic::getAlbum_id, id));
        if (delete == 0) {
            System.out.println("2有问题");
            throw new BizException(ExceptionType.ARTIST_DELETE_ERROR); //TODO 后面定义album_music删除失败时返回的错误状态
        }
        delete = this.albumDao.delete(Wrappers
                .<Album>lambdaQuery()
                .eq(Album::getId, id));
        if (delete == 0) {
            System.out.println("3有问题");
            throw new BizException(ExceptionType.PLAYLIST_DELETE_ERROR); //后面定义专辑删除失败时返回的错误状态
        }

        return true;
    }

    @Override
    public boolean sava(String id, Album album) {
        //设置album表的信息
        Artist byId = this.artistService.getById(id);
        if(byId == null){
            throw new BizException(ExceptionType.ARTIST_NOT_FOUND);
        }
        album.setId(id);
        album.setCreator(byId);
        int result = this.albumDao.addAlbum(album);
        if(result != 1){
            throw new BizException(ExceptionType.PLAYLIST_NOT_FOUND); //TODO 后续定义ALBUM_NOT_FOUND状态码
        }
        return true;
    }

    @Override
    public boolean associate(String id, String musicId,String artistId) {
//        String album_id = id;
        int insert = this.albumDao.addAlbumMusic(new AlbumMusic(id,musicId));
        if (insert != 1) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        //设置album_artist表的信息
        insert = this.albumDao.addAlbumArtist(new AlbumArtist(id,artistId));
        if (insert != 1) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        return true;
    }

    @Override
    public boolean removeMusic(String id, String musicId) {
        int insert = this.albumMusicDao.delete(Wrappers
                .<AlbumMusic>lambdaQuery()
                .eq(AlbumMusic::getAlbum_id, id)
                .eq(AlbumMusic::getMusic_id, musicId));
        if (insert != 1) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        return true;
    }

    @Override
    public boolean updateAlbumById(Album album) {
        return this.albumDao.updateAlbum(album) == 1;
    }

    @Autowired
    public void setAlbumDao(AlbumDao albumDao) {this.albumDao = albumDao;}

    @Autowired
    public void setArtistService(ArtistService artistService) {this.artistService = artistService;}

    @Autowired
    public void setAlbumMusicDao(AlbumMusicDao albumMusicDao) {this.albumMusicDao = albumMusicDao;}

    @Autowired
    public void setAlbumArtistDao(AlbumArtistDao albumArtistDao) {this.albumArtistDao = albumArtistDao;}
}
