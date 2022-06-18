package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.dto.user.*;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.vo.user.UserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

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
public interface UserService extends UserDetailsService, IService<User> {

    UserDto create(UserCreateRequest userCreateRequest);

    @Override
    User loadUserByUsername(String username);

    UserDto loadUserByOpenId(String openId);

    String createTokenByOpenId(String openId);

    boolean registeredUserWithOpenId(String openId, WeChatUserCreateRequest weChatUserCreateRequest);

    UserDto get(String id);

    UserDto update(String id, UserUpdateRequest userUpdateRequest);

    void delete(String id);

    Page<UserVo> search(Page pageable);

    String createToken(TokenCreateRequest tokenCreateRequest);

    UserDto getCurrentUser();

    List<User> list();

    Page<UserDto> page(Page page);
}
