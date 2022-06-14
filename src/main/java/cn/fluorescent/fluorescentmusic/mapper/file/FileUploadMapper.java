package cn.fluorescent.fluorescentmusic.mapper.file;

import cn.fluorescent.fluorescentmusic.dto.file.FileUploadDto;
import cn.fluorescent.fluorescentmusic.vo.file.FileUploadVo;
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
public interface FileUploadMapper {

    FileUploadVo toVo(FileUploadDto fileUploadDto);

}
