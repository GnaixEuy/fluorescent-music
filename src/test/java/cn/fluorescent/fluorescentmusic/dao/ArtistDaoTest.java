package cn.fluorescent.fluorescentmusic.dao;

import cn.fluorescent.fluorescentmusic.entity.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
@SpringBootTest
class ArtistDaoTest {

    @Autowired
    ArtistDao artistDao;

    @Test
    public void select() {
        List<Artist> artists = this.artistDao.selectList(null);
        artists.forEach(System.out::println);
    }
}