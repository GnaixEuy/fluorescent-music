package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.entity.PlayList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/19
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public interface PlayListService extends IService<PlayList> {

    boolean sava(String id, PlayList playList);

    /**
     * 删除音乐和歌单的关联
     *
     * @param id 歌单id
     * @return
     */
    boolean clear(String id);

    /**
     * 增加音乐和歌单的关联
     *
     * @param id      歌单id
     * @param musicId 音乐id
     * @return
     */
    boolean associate(String id, String musicId);

    /**
     * 移除音乐和歌单的关联
     *
     * @param id      歌单id
     * @param musicId 音乐id
     * @return
     */
    boolean removeMusic(String id, String musicId);

    /**
     * 通过用户id 获取 播放列表集合
     * @param id
     * @return
     */
//    List<PlayList> listByUserId(String id);
}
