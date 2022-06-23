package cn.fluorescent.fluorescentmusic.dto.user;

import lombok.Data;

import javax.validation.constraints.Size;

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
public class UserUpdateRequest {

    //    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 64, message = "用户昵称长度应该在4个字符到64个字符之间")
    private String nickname;

    @Size(min = 6, max = 36, message = "用户密码长度应该在6个字符到36个字符之间")
    private String password;

    private String gender;

    private Boolean locked;

    private Boolean enabled;

    private String avatarUrl;

}