package cn.fluorescent.fluorescentmusic.entity;

import cn.fluorescent.fluorescentmusic.enmus.FileStatus;
import cn.fluorescent.fluorescentmusic.enmus.FileType;
import cn.fluorescent.fluorescentmusic.enmus.Storage;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName(value = "file", resultMap = "fileResultMap")
public class File extends TraceableBaseEntity {

    private String name;

    @TableField(value = "file_key")
    private String key;

    private String ext;

    private Long size;

    private FileType type;

    private Storage storage;

    @TableField(value = "status")
    private FileStatus fileStatus = FileStatus.UPLOADING;

    public Date getCreadedTime() {
        return super.getCreatedTime();
    }

    public Date getUpdatedTime() {
        return super.getUpdatedTime();
    }

    public String getId() {
        return super.getId();
    }
}
