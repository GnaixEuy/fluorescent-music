package cn.fluorescent.fluorescentmusic.dto.Album;

import cn.fluorescent.fluorescentmusic.dto.BaseDto;
import cn.fluorescent.fluorescentmusic.entity.Music;
import cn.fluorescent.fluorescentmusic.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class AlbumDto extends BaseDto {
    private String album_name;


    private String description;


    private String album_url;

    private String creator;
}
