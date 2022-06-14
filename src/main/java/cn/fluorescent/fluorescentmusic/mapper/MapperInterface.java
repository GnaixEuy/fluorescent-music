package cn.fluorescent.fluorescentmusic.mapper;

import cn.fluorescent.fluorescentmusic.dto.BaseDto;
import cn.fluorescent.fluorescentmusic.entity.BaseEntity;
import org.mapstruct.MappingTarget;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public interface MapperInterface<Entity extends BaseEntity, Dto extends BaseDto> {
    Dto toDto(Entity entity);

    Entity toEntity(Dto dto);

    Entity updateEntity(@MappingTarget Entity entity, Dto dto);
}
