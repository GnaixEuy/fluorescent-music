package cn.fluorescent.fluorescentmusic.vo.Album;

import cn.fluorescent.fluorescentmusic.entity.Music;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.vo.BaseVo;
import cn.fluorescent.fluorescentmusic.vo.MusicVo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumVo extends BaseVo implements Serializable {
    private String name;

    private String description;

    private String album_url;

    private String creator;
}
