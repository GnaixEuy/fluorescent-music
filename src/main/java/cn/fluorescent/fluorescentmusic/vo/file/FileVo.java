package cn.fluorescent.fluorescentmusic.vo.file;

import cn.fluorescent.fluorescentmusic.enmus.FileStatus;
import cn.fluorescent.fluorescentmusic.enmus.FileType;
import cn.fluorescent.fluorescentmusic.enmus.Storage;
import cn.fluorescent.fluorescentmusic.vo.BaseVo;
import lombok.*;

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
public class FileVo extends BaseVo {

    private String name;

    private String key;

    private String uri;

    private Storage storage;

    private String ext;

    private Long size;

    private FileType type;

    private FileStatus status;

}
