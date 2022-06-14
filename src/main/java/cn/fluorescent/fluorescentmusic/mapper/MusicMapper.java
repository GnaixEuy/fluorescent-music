package cn.fluorescent.fluorescentmusic.mapper;

import cn.fluorescent.fluorescentmusic.dto.music.MusicCreateRequest;
import cn.fluorescent.fluorescentmusic.dto.music.MusicDto;
import cn.fluorescent.fluorescentmusic.dto.music.MusicUpdateRequest;
import cn.fluorescent.fluorescentmusic.entity.Music;
import cn.fluorescent.fluorescentmusic.mapper.file.FileMapper;
import cn.fluorescent.fluorescentmusic.vo.MusicVo;
import org.mapstruct.Mapper;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper(componentModel = "spring", uses = FileMapper.class)
public interface MusicMapper extends MapperInterface<Music, MusicDto> {

    MusicDto toDto(MusicCreateRequest musicCreateRequest);

    MusicDto toDto(MusicUpdateRequest musicUpdateRequest);

    MusicVo toVo(MusicDto musicDto);

}
