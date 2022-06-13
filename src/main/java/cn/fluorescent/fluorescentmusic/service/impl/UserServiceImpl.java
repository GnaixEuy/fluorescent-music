package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.config.SecurityConfig;
import cn.fluorescent.fluorescentmusic.dao.UserDao;
import cn.fluorescent.fluorescentmusic.dto.user.TokenCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserDto;
import cn.fluorescent.fluorescentmusic.dto.user.UserUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmu.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmu.Gender;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.UserMapper;
import cn.fluorescent.fluorescentmusic.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Lazy, @Autowired})
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    private final UserMapper userMapper;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDto create(UserCreateRequest userCreateRequest) {
        final String username = userCreateRequest.getUsername();
        this.checkUserName(username);
        try {
            final User user = this.userMapper.createEntity(userCreateRequest);
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            final int result = this.userDao.insert(user);
            if (result == 1) {
                final User resultUser = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
                if (ObjectUtil.isNotNull(resultUser)) {
                    return this.userMapper.toDto(resultUser);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(ExceptionType.USER_INSERT_ERROR);
        }
        throw new BizException(ExceptionType.USER_INSERT_ERROR);
    }

    /**
     * TODO: 等待重构
     *
     * @param id
     * @param userUpdateRequest
     * @return
     */
    @Override
    public UserDto update(String id, UserUpdateRequest userUpdateRequest) {
        final User user = this.userDao.selectById(id);
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        final String username = userUpdateRequest.getUsername();
        final String nickname = userUpdateRequest.getNickname();
        final String gender = userUpdateRequest.getGender();
        if (!StrUtil.isBlank(username)) {
            user.setUsername(username);
        }
        if (!StrUtil.isBlank(nickname)) {
            user.setNickname(nickname);
        }
        if (!StrUtil.isBlank(gender)) {
            user.setGender(Gender.valueOf(gender));
        }
        final boolean success = this.userDao.updateById(user) == 1;
        if (success) {
            return this.userMapper.toDto(user);
        }
        throw new BizException(ExceptionType.USER_UPDATE_ERROR);
    }

    @Override
    public void delete(String id) {
        this.userDao.deleteById(getById(id));
    }

    private void checkUserName(String username) {
        // Todo: 重构
        final List<User> users = this.userDao.selectByMap(new HashMap(1) {{
            put("username", username);
        }});
        if (!users.isEmpty()) {
            throw new BizException(ExceptionType.USER_NAME_DUPLICATE);
        }
    }

    @Override
    public User loadUserByUsername(String username) {
        final User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        } else {
            return user;
        }
    }

    @Override
    public UserDto get(String id) {
        return this.userMapper.toDto(getById(id));
    }

    @Override
    public String createToken(TokenCreateRequest tokenCreateRequest) {
        final User user = this.loadUserByUsername(tokenCreateRequest.getUsername());
        if (!passwordEncoder.matches(tokenCreateRequest.getPassword(), user.getPassword())) {
            throw new BizException(ExceptionType.USER_PASSWORD_NOT_MATCH);
        }
        if (!user.isEnabled()) {
            throw new BizException(ExceptionType.USER_NOT_ENABLED);
        }
        if (!user.isAccountNonLocked()) {
            throw new BizException(ExceptionType.USER_LOCKED);
        }
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));
    }

    @Override
    public UserDto getCurrentUser() {
        return this.userMapper.toDto(this.getCurrentUserEntity());
    }

    private User getById(String id) {
        final User user = this.userDao.selectById(id);
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return user;
    }

    protected User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // todo
        return loadUserByUsername(authentication.getName());
    }

}


