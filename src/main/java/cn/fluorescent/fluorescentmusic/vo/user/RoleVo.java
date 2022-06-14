package cn.fluorescent.fluorescentmusic.vo.user;

import cn.fluorescent.fluorescentmusic.vo.BaseVo;
import lombok.*;

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
public class RoleVo extends BaseVo {

    private String id;

    private String name;

    private String title;
}
