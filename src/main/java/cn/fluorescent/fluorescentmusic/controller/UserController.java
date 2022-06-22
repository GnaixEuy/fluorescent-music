package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.user.UserCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserDto;
import cn.fluorescent.fluorescentmusic.dto.user.UserUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.UserRole;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.UserMapper;
import cn.fluorescent.fluorescentmusic.service.RoleService;
import cn.fluorescent.fluorescentmusic.service.UserRoleService;
import cn.fluorescent.fluorescentmusic.service.UserService;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import cn.fluorescent.fluorescentmusic.vo.user.UserVo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
@RestController
@RequestMapping(value = {"/users"})
@Api(tags = {"用户"})
public class UserController {

    public static final String DEFAULT_AVATAR_URL = "https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2AsOsGfmqB4Qf3vBHLJuPvHmquJ.png";

    private UserService userService;
    private RoleService roleService;
    private UserRoleService userRoleService;

    private UserMapper userMapper;


    @GetMapping(value = {"/all"})
    @Cacheable(cacheNames = {"userListCache"})
    @ApiOperation(value = "获取全部用户信息，以uservo 形式展示")
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<List<UserVo>> list() {
        return ResponseResult.success(userService.list()
                .stream()
                .map(this.userMapper::toDto)
                .map(this.userMapper::toVo)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = {""})
    @ApiOperation(value = "用户分页检索，传入对应的size、total等参数获取需要的用户分页数据")
    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult<Page<UserVo>> search(Page page) {
        return ResponseResult.success(this.userService.search(page));

    }

    @PostMapping(value = {""})
    @Transactional
    @CacheEvict(cacheNames = {"userListCache"}, allEntries = true)
    @ApiOperation(value = "创建user接口,以json的形式使用POST传入，返回创建成功的vo", httpMethod = "POST")
    public UserVo create(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        userCreateRequest.setAvatarUrl(UserController.DEFAULT_AVATAR_URL);
        UserDto userDto = this.userService.create(userCreateRequest);
        UserRole userRole = this.userRoleService.getOne(Wrappers
                .<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, userDto.getId())
                .eq(UserRole::getRoleId, "1"));
        if (userRole == null) {
            this.userRoleService.save(new UserRole(userDto.getId(), "1"));
        }
        return this.userMapper.toVo(userDto);
    }

    @GetMapping(value = {"/{id}"})
    @Cacheable(value = {"userInfo"}, key = "#id")
    @RolesAllowed(value = {"ROLE_ADMIN"})
    @ApiOperation(value = "通过id获取UserVo信息", httpMethod = "GET")
    public UserVo get(@PathVariable String id) {
        final UserDto userDto = this.userService.get(id);
        if (userDto == null) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        } else {
            return this.userMapper.toVo(userDto);
        }
    }

    /**
     * 时间自动更新bug 已修复，待全面测试
     */
    @PutMapping(value = {"/{id}"})
    @CacheEvict(cacheNames = {"userListCache", "userInfo"}, allEntries = true)
    @ApiOperation(value = "通过id 更新user数据，返回更新后的vo", httpMethod = "PUT")
//    @RolesAllowed(value = {"ROLE_ADMIN"})
    public UserVo update(@PathVariable String id, @Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        final UserDto userDto = this.userService.update(id, userUpdateRequest);
        return this.userMapper.toVo(userDto);
    }

    @DeleteMapping(value = {"/{id}"})
    @CacheEvict(cacheNames = {"userListCache", "userInfo"}, allEntries = true)
    @ApiOperation(value = "通过id，删除user", httpMethod = "DELETE")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public void delete(@PathVariable String id) {
        this.userService.delete(id);
    }

    @GetMapping(value = {"/me"})
    @ApiOperation(value = "通过请求头保存的token，获取当前用户的信息", httpMethod = "GET")
    public UserVo me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return this.userMapper.toVo(this.userService.getCurrentUser());
    }

    @GetMapping(value = {"/bind/{id}/{title}"})
    @ApiOperation(value = "传入用户id，和角色姓名进行绑定")
    public ResponseResult<String> bindRole(@PathVariable String id, @PathVariable String title) {
        this.userService.bindRole(id, title);
        return ResponseResult.success("绑定成功");
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
}

