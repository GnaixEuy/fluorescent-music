package cn.fluorescent.fluorescentmusic.utils;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.ksuid.KsuidGenerator;
import org.springframework.stereotype.Component;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Component
public class KsuidIdentifierGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return new DefaultIdentifierGenerator().nextId(entity);
    }

    @Override
    public String nextUUID(Object entity) {
        return KsuidGenerator.generate();
    }
}