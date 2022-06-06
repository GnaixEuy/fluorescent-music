package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.dto.user.TokenCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserDto;
import cn.fluorescent.fluorescentmusic.dto.user.UserUpdateRequest;
import cn.fluorescent.fluorescentmusic.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public interface UserService extends UserDetailsService, IService<User> {
    UserDto create(UserCreateRequest userCreateRequest);


    @Override
    User loadUserByUsername(String username);

    UserDto get(String id);

    UserDto update(String id, UserUpdateRequest userUpdateRequest);

    void delete(String id);

    String createToken(TokenCreateRequest tokenCreateRequest);

    UserDto getCurrentUser();
}