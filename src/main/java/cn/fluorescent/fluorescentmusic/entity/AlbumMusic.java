package cn.fluorescent.fluorescentmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "album_music")
public class AlbumMusic extends BaseEntity{
    private String album_id;

    private String music_id;
}
