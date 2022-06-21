package cn.fluorescent.fluorescentmusic.dto.user;

import cn.fluorescent.fluorescentmusic.enmus.Gender;
import lombok.Data;

import java.util.Date;
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
@Data
public class UserDto {

    private String id;

    private String username;

    private String nickname;

    private List <RoleDto> roles;

    private Gender gender;

    private Boolean locked;

    private Boolean enabled;

    private String lastLoginIp;

    private Date lastLoginTime;

    private String avatarUrl;

}
