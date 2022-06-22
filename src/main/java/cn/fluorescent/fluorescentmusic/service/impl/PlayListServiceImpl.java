package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.dao.PlayListDao;
import cn.fluorescent.fluorescentmusic.dao.PlayListMusicDao;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.PlayList;
import cn.fluorescent.fluorescentmusic.entity.PlaylistMusic;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.PlayListService;
import cn.fluorescent.fluorescentmusic.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/19
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Service
public class PlayListServiceImpl extends ServiceImpl<PlayListDao, PlayList> implements PlayListService {

    private UserService userService;
    private PlayListDao playListDao;

    private PlayListMusicDao playListMusicDao;

    /**
     * @param id
     * @param playList
     * @return
     */
    @Override
    public boolean sava(String id, PlayList playList) {
        User byId = this.userService.getById(id);
        if (byId == null) {
            throw new BizException(ExceptionType.ARTIST_NOT_FOUND);
        }
        playList.setCreator(byId);
        int result = this.playListDao.addPlayList(playList);
        if (result != 1) {
            throw new BizException(ExceptionType.PLAYLIST_NOT_FOUND);
        }
        return true;
    }

    /**
     * 删除音乐和歌单的关联
     *
     * @param id 歌单id
     * @return
     */
    @Override
    public boolean clear(String id) {
        this.playListMusicDao.delete(Wrappers
                .<PlaylistMusic>lambdaQuery()
                .eq(PlaylistMusic::getPlaylistId, id));
        return true;
    }

    /**
     * 增加音乐和歌单的关联
     *
     * @param id      歌单id
     * @param musicId 音乐id
     * @return
     */
    @Override
    public boolean associate(String id, String musicId) {
        int insert = this.playListMusicDao.insert(new PlaylistMusic(id, musicId));
        if (insert != 1) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        return true;
    }

    /**
     * 移除音乐和歌单的关联
     *
     * @param id      歌单id
     * @param musicId 音乐id
     * @return
     */
    @Override
    public boolean removeMusic(String id, String musicId) {
        int insert = this.playListMusicDao.delete(Wrappers
                .<PlaylistMusic>lambdaQuery()
                .eq(PlaylistMusic::getPlaylistId, id)
                .eq(PlaylistMusic::getMusicId, musicId));
        if (insert != 1) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        return true;
    }

    /**
     * 通过用户id 获取 播放列表集合
     *
     * @param playList@return
     */
    @Override
    public boolean updatePlayListById(PlayList playList) {
        return this.playListDao.updatePlayList(playList) == 1;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPlayListDao(PlayListDao playListDao) {
        this.playListDao = playListDao;
    }

    @Autowired
    public void setPlayListMusicDao(PlayListMusicDao playListMusicDao) {
        this.playListMusicDao = playListMusicDao;
    }
}
