package cn.fluorescent.fluorescentmusic.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Gender {
    /**
     * 数据库-显示: 1-女, 2-男, 3-未知
     */
    FEMALE(1, "女"),
    MALE(2, "男"),
    UNKNOWN(3, "未知");

    @EnumValue
    private Integer key;

    @JsonValue
    private String display;

}

