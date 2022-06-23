package cn.fluorescent.fluorescentmusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "album_artist")
public class AlbumArtist extends BaseEntity{
    private String album_id;
    private String artist_id;
}
