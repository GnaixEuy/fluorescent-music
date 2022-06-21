package cn.fluorescent.fluorescentmusic.dao;

import cn.fluorescent.fluorescentmusic.entity.Artist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/20
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper
public interface ArtistDao extends BaseMapper<Artist> {

    int registeredArtist(Artist artist);

}
