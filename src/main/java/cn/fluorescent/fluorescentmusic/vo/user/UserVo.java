package cn.fluorescent.fluorescentmusic.vo.user;

import cn.fluorescent.fluorescentmusic.enmus.Gender;
import cn.fluorescent.fluorescentmusic.vo.BaseVo;
import lombok.*;

import java.io.Serializable;
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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends BaseVo implements Serializable {

    private String username;

    private String nickname;

    private String avatarUrl;

    private Gender gender;

    private Boolean locked;

    private Boolean enabled;

    private List<RoleVo> roles;
}

