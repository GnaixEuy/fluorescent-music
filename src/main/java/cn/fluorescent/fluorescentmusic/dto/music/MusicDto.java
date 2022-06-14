package cn.fluorescent.fluorescentmusic.dto.music;

import cn.fluorescent.fluorescentmusic.dto.BaseDto;
import cn.fluorescent.fluorescentmusic.dto.file.FileDto;
import cn.fluorescent.fluorescentmusic.enmus.MusicStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
public class MusicDto extends BaseDto {

    private String name;

    private MusicStatus status = MusicStatus.DRAFT;

    private String description;

    private FileDto file;

}

