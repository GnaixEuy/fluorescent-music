package cn.fluorescent.fluorescentmusic.dao;

import cn.fluorescent.fluorescentmusic.enmu.Gender;
import cn.fluorescent.fluorescentmusic.entity.User;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@SpringBootTest
@Transactional
class UserDaoTest {

    @Autowired
    UserDao userDao;


    @Test
    public void insert(){
        User user = new User("测试11111","happy测试1","123123123",Gender.MALE,Boolean.FALSE,Boolean.TRUE,null,null,null);
        int insert = this.userDao.insert(user);
        Assertions.assertEquals(insert, 1);
    }

    @Test
    public void select(){
        List<User> users = this.userDao.selectList(null);
        Assertions.assertNotNull(users);
        users.forEach(System.out::println);
    }

    @Test
    public void update(){
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, "admin22"));
        Assertions.assertNotNull(user);
        user.setPassword("111111111");
        int i = this.userDao.updateById(user);
        Assertions.assertEquals(i, 1);
    }

    @Test
    public  void  delete(){
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, "admin23"));
        Assertions.assertNotNull(user);
        int i = this.userDao.deleteById(user);
        Assertions.assertEquals(i, 1);
    }

}