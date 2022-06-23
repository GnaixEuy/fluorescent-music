package cn.fluorescent.fluorescentmusic.dao;

import cn.fluorescent.fluorescentmusic.entity.Album;
import cn.fluorescent.fluorescentmusic.entity.AlbumArtist;
import cn.fluorescent.fluorescentmusic.entity.AlbumMusic;
import cn.fluorescent.fluorescentmusic.vo.Album.AlbumVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AlbumDao extends BaseMapper<Album> {

    String findAlbumNameById(int id); //查询该专辑名称

    String findDescriptionById(int id); //查询专辑简介

    String findUrlById(int id);//获取url

    String findNickNameBuId(int id); //获取音乐人昵称

    List<Map<String,String>> findMusicInfo(int id);//获取专辑中所有音乐名称及url

    Album findAlbumInfoById(String id);//获取对应id的专辑内容

    String findAlbumArtistNameById(String id);

    int addAlbum(Album album);

    int updateAlbum(Album album);

//    int addAlbumArtist(String album_id,String artist_id);

    int getArtistById(String id);

    int addAlbumArtist(AlbumArtist artist);

    int addAlbumMusic(AlbumMusic albumMusic);
}
