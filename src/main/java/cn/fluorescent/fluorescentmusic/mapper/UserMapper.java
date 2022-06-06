package cn.fluorescent.fluorescentmusic.mapper;

import cn.fluorescent.fluorescentmusic.dto.user.UserCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.user.UserDto;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.vo.UserVo;
import org.mapstruct.Mapper;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    UserVo toVo(UserDto userDto);

    User createEntity(UserCreateRequest userCreateRequest);
}

