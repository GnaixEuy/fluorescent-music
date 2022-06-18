package cn.fluorescent.fluorescentmusic.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/18
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@AllArgsConstructor
@NotBlank
@Data
public class WeChatUserCreateRequest {

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private Integer gender;

    private String avatarUrl;

}
