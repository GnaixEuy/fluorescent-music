package cn.fluorescent.fluorescentmusic.mapper.file;

import cn.fluorescent.fluorescentmusic.dto.file.FileDto;
import cn.fluorescent.fluorescentmusic.dto.file.FileUploadRequest;
import cn.fluorescent.fluorescentmusic.entity.File;
import cn.fluorescent.fluorescentmusic.vo.file.FileVo;
import org.mapstruct.DecoratedWith;
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
@Mapper(componentModel = "spring")
@DecoratedWith(FileMapperDecorator.class)
public interface FileMapper {
    File createEntity(FileUploadRequest fileUploadRequest);

    FileVo toVo(FileDto fileDto);

    FileDto toDto(File file);

    File toEntity(FileDto fileDto);

}
