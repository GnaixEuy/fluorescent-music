package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.entity.Role;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.RoleService;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/19
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@RestController
@RequestMapping(value = {"/role"})
public class RoleController {

    private RoleService roleService;


    @GetMapping(value = {"/"})
    @ApiOperation(value = "获取所有角色")
    public ResponseResult<List<Role>> list() {
        return ResponseResult.success(this.roleService.list());

    }

    @PostMapping(value = {"/"})
    @ApiOperation(value = "添加角色信息")
    public ResponseResult<String> create(Role role) {
        boolean save = this.roleService.save(role);
        if (!save) {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
        return ResponseResult.success("创建角色信息成功");
    }

    @PostMapping(value = {"/{title}"})
    @ApiOperation(value = "通过名称删除角色信息，有用户使用该角色信息的时候删除会出现异常")
    public ResponseResult<String> delete(@PathVariable String title) {
        boolean remove = this.roleService.remove(Wrappers.<Role>lambdaQuery().eq(Role::getName, title));
        if (!remove) {
            throw new BizException(ExceptionType.USER_DELETE_ERROR);
        }
        return ResponseResult.success("删除角色信息成功");
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
