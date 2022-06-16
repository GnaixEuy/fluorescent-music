package cn.fluorescent.fluorescentmusic.mapper;

import cn.fluorescent.fluorescentmusic.entity.File;
import cn.fluorescent.fluorescentmusic.vo.file.FileVo;
import org.mapstruct.Mapper;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/16
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Mapper(componentModel = "spring")
public interface FileMapper {

    FileVo toVo(File file);

}
