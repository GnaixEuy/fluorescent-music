package cn.fluorescent.fluorescentmusic.dao;

import cn.fluorescent.fluorescentmusic.entity.Music;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/13
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@SpringBootTest
class MusicDaoTest {

    @Autowired
    MusicDao musicDao;

    @Test
    public void selectList(){
        List<Music> music = this.musicDao.selectList(null);
        Assertions.assertNotNull(music);
        music.forEach(System.out::println);
    }

}