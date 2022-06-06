package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.user.UserCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserDto;
import cn.fluorescent.fluorescentmusic.dto.user.UserUpdateRequest;
import cn.fluorescent.fluorescentmusic.enmu.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.UserMapper;
import cn.fluorescent.fluorescentmusic.service.UserService;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import cn.fluorescent.fluorescentmusic.vo.UserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequiredArgsConstructor(onConstructor_ = {@Lazy, @Autowired})
public class UserController {

    private  UserService userService;

    private  UserMapper userMapper;


	@GetMapping(value = {"/all"})
    //    @RolesAllowed(value = {"ROLE_ADMIN"})
	public ResponseResult< List<UserVo>> list() {
		return ResponseResult.success( userService.list()
				.stream()
				.map(this.userMapper::toDto)
				.map(this.userMapper::toVo)
				.collect(Collectors.toList()));
	}

    @GetMapping(value = {""})
    @ApiOperation(value = "用户检索")
//    @RolesAllowed(value = {"ROLE_ADMIN"})
    public ResponseResult< Page<User> > search(Page<User> page) {
        page = this.userService.page(page);
        return ResponseResult.success( page);
    }

    @PostMapping(value = {""})
//    @RolesAllowed(value = {"ROLE_ADMIN"})
    public UserVo create(@Validated @RequestBody UserCreateRequest userCreateRequest) {
        return this.userMapper.toVo(this.userService.create(userCreateRequest));
    }

    @GetMapping(value = {"/{id}"})
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
//    @RolesAllowed(value = {"ROLE_ADMIN"})
    public UserVo update(@PathVariable String id, @Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        final UserDto userDto = this.userService.update(id, userUpdateRequest);
        return this.userMapper.toVo(userDto);
    }

    @DeleteMapping(value = {"/{id}"})
//    @RolesAllowed(value = {"ROLE_ADMIN"})
    void delete(@PathVariable String id) {
        this.userService.delete(id);
    }

    @GetMapping(value = {"/me"})
    UserVo me() {
        return this.userMapper.toVo(this.userService.getCurrentUser());
    }



    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

}

