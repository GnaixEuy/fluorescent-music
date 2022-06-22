package cn.fluorescent.fluorescentmusic.entity;

import cn.fluorescent.fluorescentmusic.enmus.PlayListStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/19
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "playlist", resultMap = "playListResultMap")
public class PlayList extends BaseEntity implements Serializable {

    private String name;
    private String description;
    private String coverUrl;
    private PlayListStatus status = PlayListStatus.DRAFT;
    private User creator;
    private String type;

    @TableField(exist = false)
    private List<Music> musicList;

    public PlayList(String name) {
        this.name = name;
    }
}
