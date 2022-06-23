package cn.fluorescent.fluorescentmusic.entity;

import cn.fluorescent.fluorescentmusic.enmus.AlbumStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@TableName(value = "album", resultMap = "albumResultMap")
@Data
public class Album extends BaseEntity {


    private String name;

    private String description;

    private String album_url;

    private AlbumStatus status;

    private Artist creator;

    private String created_time;

    private String updated_time;
}
