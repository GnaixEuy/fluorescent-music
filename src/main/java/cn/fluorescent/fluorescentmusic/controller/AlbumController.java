package cn.fluorescent.fluorescentmusic.controller;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import cn.fluorescent.fluorescentmusic.dao.AlbumArtistDao;
import cn.fluorescent.fluorescentmusic.dao.AlbumDao;
import cn.fluorescent.fluorescentmusic.dto.AlbumCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.AlbumUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmus.AlbumStatus;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmus.PlayListStatus;
import cn.fluorescent.fluorescentmusic.entity.Album;
import cn.fluorescent.fluorescentmusic.entity.AlbumArtist;
import cn.fluorescent.fluorescentmusic.entity.Artist;
import cn.fluorescent.fluorescentmusic.entity.PlayList;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.AlbumService;
import cn.fluorescent.fluorescentmusic.service.ArtistService;
import cn.fluorescent.fluorescentmusic.vo.Album.AlbumVo;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/album")
//TODO 添加专辑状态异常的处理信息
public class AlbumController {
    private AlbumService albumService;

    private ArtistService artistService;

    private AlbumDao albumDao;


    @GetMapping("/search/{id}")
    //获取指定id对应专辑的所有信息
    public ResponseResult<AlbumVo> searchAlbumById(@PathVariable String id) {
        return ResponseResult.success(this.albumService.searchAlbumInfoById(id));
    }

    @GetMapping("/page_search")
    //专辑
    public ResponseResult<Page<Album>> pageSearchAlbumById(Page<Album> page) {
        return ResponseResult.success(this.albumService.page(page));
    }

    @PutMapping("/create/{id}")
    @Transactional
    public ResponseResult<String> create(@PathVariable @NotBlank String id, @RequestBody AlbumCreateRequest albumCreateRequest) {
        System.out.println("所以id是：" + id);
        Album album = new Album();
        album.setName(albumCreateRequest.getName());
        album.setDescription(albumCreateRequest.getDescription());
        album.setAlbum_url(albumCreateRequest.getCover_url());
        album.setStatus(statusTranslator(Integer.valueOf(albumCreateRequest.getStatus())));
        boolean sava = this.albumService.sava(id, album);
        if (!sava) {
            throw new BizException(ExceptionType.PLAYLIST_CREATE_ERROR);
        }
        return ResponseResult.success("专辑创建成功!!!!!?");
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseResult<String> delete(@PathVariable String id) {
        boolean clear = this.albumService.clear(id);
        if (!clear) {
            throw new BizException(ExceptionType.PLAYLIST_DELETE_ERROR);//TODO 自定义ALBUM_DELETE_ERROR状态码
        }
        return ResponseResult.success("专辑删除成功！");
    }

    //向专辑添加歌曲和歌手关联（即向album_artist和album_music中添加信息）
    @PutMapping(value = {"/add/{id}/{musicId}"})
    @Transactional
    public ResponseResult<String> add(@PathVariable @NotBlank String id,
                                      @PathVariable @NotBlank String musicId) {
        try {
            String artistId = id;
            this.albumService.associate(id, musicId, artistId);
        } catch (BizException e) {
            throw new BizException(ExceptionType.PLAYLIST_UPDATE_ERROR);
        }
        return ResponseResult.success("添加专辑音乐成功");
    }

    @PutMapping("/updateInfo/{id}")
    @Transactional
    public ResponseResult<String> update(@PathVariable @NotBlank String id, @RequestBody AlbumUpdateRequest albumUpdateRequest) {
        Album byId = this.albumService.getById(id);
        //自己写一个来获取当前的Artist的id
        int artist_id = 0;
        artist_id = this.albumDao.getArtistById(id);
        if (artist_id == 0) {
            throw new BizException(ExceptionType.ARTIST_NOT_FOUND);
        }
        Artist artist = this.artistService.getById(artist_id);

        if (byId == null) {
            throw new BizException(ExceptionType.PLAYLIST_NOT_FOUND);
        }
        if (StrUtil.isNotBlank(albumUpdateRequest.getName())) {
            byId.setName(albumUpdateRequest.getName());
        }
        if (StrUtil.isNotBlank(albumUpdateRequest.getDescription())) {
            byId.setDescription(albumUpdateRequest.getDescription());
        }
        if (StrUtil.isNotBlank(albumUpdateRequest.getCoverUrl())) {
            byId.setAlbum_url(albumUpdateRequest.getCoverUrl());
        }
        byId.setCreator(artist);
        byId.setStatus(this.statusTranslator(Integer.parseInt(albumUpdateRequest.getStatus())));
        boolean updateResult = this.albumService.updateAlbumById(byId);
        if (!updateResult) {
            throw new BizException(ExceptionType.PLAYLIST_UPDATE_ERROR); //TODO 添加ALBUM_UPDATE_ERROR状态码
        }
        return ResponseResult.success("专辑信息更新成功");
    }

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Autowired
    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }


    private AlbumStatus statusTranslator(Integer status) {
        AlbumStatus albumStatus;
        switch (status) {
            case 2:
                albumStatus = AlbumStatus.PUBLISHED;
                break;
            case 3:
                albumStatus = AlbumStatus.CLOSED;
                break;
            default:
                albumStatus = AlbumStatus.DRAFT;
        }
        return albumStatus;
    }

}
