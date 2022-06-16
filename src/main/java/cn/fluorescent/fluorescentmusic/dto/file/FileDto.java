package cn.fluorescent.fluorescentmusic.dto.file;

import cn.fluorescent.fluorescentmusic.dto.BaseDto;
import cn.fluorescent.fluorescentmusic.enmus.FileStatus;
import cn.fluorescent.fluorescentmusic.enmus.FileType;
import cn.fluorescent.fluorescentmusic.enmus.Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class FileDto extends BaseDto {

    private String name;

    private String key;

    private String uri;

    private String ext;

    private Long size;

    private FileType type;

    private Storage storage;

    private FileStatus status;

    private FileStatus fileStatus;

}