package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.config.SecurityConfig;
import cn.fluorescent.fluorescentmusic.dao.RoleDao;
import cn.fluorescent.fluorescentmusic.dao.UserDao;
import cn.fluorescent.fluorescentmusic.dao.UserRoleDao;
import cn.fluorescent.fluorescentmusic.dto.user.*;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmus.Gender;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.entity.UserRole;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.UserMapper;
import cn.fluorescent.fluorescentmusic.service.UserService;
import cn.fluorescent.fluorescentmusic.vo.user.UserVo;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private UserMapper userMapper;
    private RoleDao roleDao;

    private UserRoleDao userRoleDao;
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<UserVo> search(Page page) {
        page = this.userDao.selectPage(page, Wrappers.<User>lambdaQuery().orderByAsc(User::getCreatedTime));
        final List<UserVo> userDtoList = ((List<User>) page.getRecords())
                .stream()
                .map(this.userMapper::toDto)
                .map(this.userMapper::toVo)
                .collect(Collectors.toList());
        page.setRecords(userDtoList);
        return page;
    }

    @Override
    public UserDto create(UserCreateRequest userCreateRequest) {
        final String username = userCreateRequest.getUsername();
        this.checkUserName(username);
        try {
            User user = new User();
            user.setUsername(username);
            user.setNickname(userCreateRequest.getNickname());
            user.setPassword(this.passwordEncoder.encode(userCreateRequest.getPassword()));
            user.setAvatarUrl(userCreateRequest.getAvatarUrl());
            Gender gender;
            switch (userCreateRequest.getGender()) {
                case "1":
                    gender = Gender.FEMALE;
                    break;
                case "2":
                    gender = Gender.MALE;
                    break;
                default:
                    gender = Gender.UNKNOWN;
                    break;
            }
            user.setGender(gender);
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
        final String nickname = userUpdateRequest.getNickname();
        final String gender = userUpdateRequest.getGender();
        if (!StrUtil.isBlank(nickname)) {
            user.setNickname(nickname);
        }
        if (!StrUtil.isBlank(gender)) {
            if ("1".equals(gender)) {
                user.setGender(Gender.FEMALE);
            } else if ("2".equals(gender)) {
                user.setGender(Gender.MALE);
            } else {
                user.setGender(Gender.UNKNOWN);
            }
        }
        if (userUpdateRequest.getLocked() != null) {
            user.setLocked(userUpdateRequest.getLocked());
        }
        if (userUpdateRequest.getEnabled() != null) {
            user.setEnabled(userUpdateRequest.getEnabled());
        }
        if (userUpdateRequest.getPassword() != null) {
            user.setPassword(this.passwordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (userUpdateRequest.getAvatarUrl() != null) {
            user.setAvatarUrl(userUpdateRequest.getAvatarUrl());
        }
        final boolean success = this.userDao.updateById(user) == 1;
        if (success) {
            return this.userMapper.toDto(user);
        }
        throw new BizException(ExceptionType.USER_UPDATE_ERROR);
    }

    @Override
    public void delete(String id) {
        this.userRoleDao.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
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

    /**
     * @param openId
     * @return
     */
    @Override
    public UserDto loadUserByOpenId(String openId) {
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId));
        return this.userMapper.toDto(user);
    }

    /**
     * @param openId
     * @return
     */
    @Override
    public String createTokenByOpenId(String openId) {
        User user = this.userDao.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId));
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return tokenVerifyAndGenerated(user);
    }

    /**
     * @param openId
     * @param weChatUserCreateRequest
     * @return
     */
    @Override
    public boolean registeredUserWithOpenId(String openId, WeChatUserCreateRequest weChatUserCreateRequest) {
        User user = new User();
        user.setNickname(weChatUserCreateRequest.getNickname());
        user.setUsername(openId);
        switch (weChatUserCreateRequest.getGender()) {
            case 0:
                user.setGender(Gender.MALE);
                break;
            case 1:
                user.setGender(Gender.FEMALE);
                break;
            default:
                user.setGender(Gender.UNKNOWN);
        }
        user.setAvatarUrl(weChatUserCreateRequest.getAvatarUrl());
        user.setOpenId(openId);
        user.setPassword(openId);
        user.setLocked(Boolean.FALSE);
        user.setEnabled(Boolean.TRUE);
        boolean save = this.save(user);
        if (save) {
            return true;
        }
        throw new BizException(ExceptionType.USER_INSERT_ERROR);

    }

    @Override
    public UserDto get(String id) {
        return this.userMapper.toDto(this.getById(id));
    }

    @Override
    public String createToken(TokenCreateRequest tokenCreateRequest) {
        final User user = this.loadUserByUsername(tokenCreateRequest.getUsername());
        if (!passwordEncoder.matches(tokenCreateRequest.getPassword(), user.getPassword())) {
            throw new BizException(ExceptionType.USER_PASSWORD_NOT_MATCH);
        }
        return tokenVerifyAndGenerated(user);
    }

    private String tokenVerifyAndGenerated(User user) {
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

    @Override
    public List<User> list() {
        return this.userDao.selectList(null);
    }

    /**
     * @param page
     * @return
     */
    @Override
    public Page<UserDto> page(Page page) {
        Page retPage = this.userDao.selectPage(page, null);
        List<User> records = retPage.getRecords();
        List<UserDto> collect = records.stream().map(this.userMapper::toDto).collect(Collectors.toList());
        return retPage.setRecords(collect);
    }

    /**
     * 给用户绑定角色信息
     *
     * @param id    用户id
     * @param title 角色名称title
     * @return
     */
    @Override
    @CacheEvict(cacheNames = {"userListCache"}, allEntries = true)
    public boolean bindRole(String id, String title) {
        int insert = this.userRoleDao.insert(new UserRole(id, title));
        if (insert != 1) {
            throw new BizException(ExceptionType.USER_ROLE_BIND_ERROR);
        }
        return true;

    }

    private User getById(String id) {
        final User user = this.userDao.selectById(id);
        if (user == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return user;
    }

    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // todo
        return loadUserByUsername(authentication.getName());
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

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}

