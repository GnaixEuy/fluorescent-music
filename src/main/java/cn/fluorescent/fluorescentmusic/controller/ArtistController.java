package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.ArtistUpdateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserDto;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmus.Gender;
import cn.fluorescent.fluorescentmusic.enmus.GeneralStatus;
import cn.fluorescent.fluorescentmusic.entity.*;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.*;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/21
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@Api(tags = "音乐人接口")
@RequestMapping(value = {"/artist"})
public class ArtistController {

    private ArtistService artistService;
    private UserService userService;
    private UserRoleService userRoleService;

    private ArtistMusicService artistMusicService;

    private MusicService musicService;

    @GetMapping(value = {"/"})
    @Cacheable(value = {"artistListCache"})
    @ApiOperation(value = "获取所有音乐人信息接口")
    public ResponseResult<List<Artist>> list() {
        return ResponseResult.success(this.artistService.list());
    }

    @GetMapping(value = {"/search"})
    @ApiOperation(value = "获取部分音乐人信息接口（分页）")
    public ResponseResult<Page<Artist>> search(Page<Artist> page) {
        return ResponseResult.success(this.artistService.page(page));
    }

    @GetMapping(value = {"/musicListByArtistId/{id}"})
    @Cacheable(value = "MusicListByArtistId", key = "#id")
    @ApiOperation(value = "传入音乐人id 获取该音乐人的所有音乐信息")
    public ResponseResult<List<Music>> listByArtistId(@PathVariable String id) {
        List<ArtistMusic> artistMusicList = this.artistMusicService.list(Wrappers
                .<ArtistMusic>lambdaQuery()
                .eq(ArtistMusic::getArtistId, id));
        List<Music> list = new ArrayList<>(artistMusicList.size());
        for (ArtistMusic artistMusic : artistMusicList) {
            list.add(this.musicService.getById(artistMusic.getMusicId()));
        }
        return ResponseResult.success(list);
    }


    @PostMapping(value = {"/{id}"})
    @Transactional
    @CacheEvict(cacheNames = {"artistListCache", "artistGenderListCache"}, allEntries = true)
    @ApiOperation(value = "注册音乐人接口，传入用户id和音乐人描述remark，可升级用户身份至音乐人")
    public ResponseResult<String> register(@PathVariable String id, @RequestBody String remark) {
        UserRole isRepeat = this.userRoleService.getOne(Wrappers
                .<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, id)
                .eq(UserRole::getRoleId, "3"));
        if (isRepeat != null) {
            throw new BizException(ExceptionType.ARTIST_ALREADY_REGISTERED);
        }
        User byId = this.userService.getById(id);
        Artist artist = new Artist();
        artist.setRemark(remark);
        artist.setName(byId.getNickname());
        artist.setCreatedBy(byId);
        artist.setUpdatedBy(byId);
        artist.setPhotoUrl(byId.getAvatarUrl());
        boolean save = this.artistService.savaArtist(artist);
        if (!save) {
            throw new BizException(ExceptionType.ARTIST_INSERT_ERROR);
        }
        boolean addRelation = this.userRoleService.save(new UserRole(id, "3"));
        if (!addRelation) {
            throw new BizException(ExceptionType.ARTIST_INSERT_ERROR);
        }
        return ResponseResult.success("注册音乐人信息成功");
    }


    @DeleteMapping(value = {"/{id}"})
    @Transactional
    @CacheEvict(cacheNames = {"artistListCache", "artistGenderListCache", "MusicListByArtistId"}, allEntries = true)
    @ApiOperation(value = "通过音乐人id 取消音乐人资格")
    public ResponseResult<String> delete(@PathVariable String id) {
        Artist artist = this.artistService.getById(id);
        User user = this.userService.getById(artist.getCreatedBy().getId());
        boolean remove = this.userRoleService.remove(Wrappers
                .<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, user.getId())
                .eq(UserRole::getRoleId, "3"));
        if (!remove) {
            throw new BizException(ExceptionType.ARTIST_DELETE_ERROR);
        }
        boolean removeById = this.artistService.removeById(id);
        if (!removeById) {
            throw new BizException(ExceptionType.ARTIST_DELETE_ERROR);
        }
        return ResponseResult.success("取消身份成功");
    }

    @PutMapping(value = {"/{id}"})
    @CacheEvict(cacheNames = {"artistListCache", "artistGenderListCache"}, allEntries = true)
    @ApiOperation(value = "通过歌手id 进行信息更新")
    public ResponseResult<String> update(@PathVariable String id,
                                         @RequestBody ArtistUpdateRequest artistUpdateRequest) {
        Artist artist = this.artistService.getById(id);
        if (artist == null) {
            throw new BizException(ExceptionType.ARTIST_NOT_FOUND);
        }
        if (StrUtil.isNotBlank(artistUpdateRequest.getName())) {
            artist.setName(artistUpdateRequest.getName());
        }
        if (StrUtil.isNotBlank(artistUpdateRequest.getRemark())) {
            artist.setRemark(artistUpdateRequest.getRemark());
        }
        if (StrUtil.isNotBlank(artistUpdateRequest.getPhotoUrl())) {
            artist.setPhotoUrl(artistUpdateRequest.getPhotoUrl());
        }
        if (StrUtil.isNotBlank(artistUpdateRequest.getStatus())) {
            GeneralStatus generalStatus;
            switch (artistUpdateRequest.getStatus()) {
                case "1":
                    generalStatus = GeneralStatus.PUBLISHED;
                    break;
                case "2":
                    generalStatus = GeneralStatus.BLOCKED;
                    break;
                default:
                    generalStatus = GeneralStatus.DRAFT;
            }
            artist.setStatus(generalStatus);
        }
        boolean update = this.artistService.updateById(artist);
        if (!update) {
            throw new BizException(ExceptionType.ARTIST_UPDATE_ERROR);
        }
        return ResponseResult.success("歌手信息更新成功");
    }

    @GetMapping(value = {"/gender/{gender}"})
    @ApiOperation(value = "通过音乐人性别获取音乐人信息")
    @Cacheable(value = {"artistGenderListCache"})
    public ResponseResult<List<Artist>> gender(@PathVariable String gender) {
        Gender genderTranslator;
        switch (gender) {
            case "男":
                genderTranslator = Gender.MALE;
                break;
            case "女":
                genderTranslator = Gender.FEMALE;
                break;
            default:
                genderTranslator = Gender.UNKNOWN;
        }
        Artist artist;
        List<User> list = this.userService.list(
                Wrappers.<User>lambdaQuery()
                        .eq(User::getGender, genderTranslator)
        );
        List<Artist> resultList = new ArrayList<>(list.size());
        for (User user : list) {
            artist = this.artistService.findArtistByUserId(user.getId());
            if (artist != null) {
                resultList.add(artist);
            }
        }
        return ResponseResult.success(resultList);
    }

    @GetMapping(value = {"/me"})
    @ApiOperation(value = "获取本身音乐人信息，如果不是音乐人返回异常")
    public ResponseResult<Artist> me() {
        UserDto currentUser = this.userService.getCurrentUser();
        Artist artistByUserId = this.artistService.findArtistByUserId(currentUser.getId());
        if (artistByUserId == null) {
            throw new BizException(ExceptionType.ARTIST_NOT_FOUND);
        }
        return ResponseResult.success(artistByUserId);
    }


    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Autowired
    public void setArtistMusicService(ArtistMusicService artistMusicService) {
        this.artistMusicService = artistMusicService;
    }

    @Autowired
    public void setMusicService(MusicService musicService) {
        this.musicService = musicService;
    }
}
