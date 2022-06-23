package cn.fluorescent.fluorescentmusic.vo;

import cn.fluorescent.fluorescentmusic.enmus.MusicStatus;
import cn.fluorescent.fluorescentmusic.vo.file.FileVo;
import lombok.*;

import java.io.Serializable;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicVo extends BaseVo implements Serializable {

    private String name;

    private String type;

    private String imageUrl;

    private MusicStatus status;

    private String description;

    private FileVo musicFile;


}

