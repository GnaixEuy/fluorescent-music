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
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MusicStatus {
    /**
     *
     */
    DRAFT(1, "草稿"),
    PUBLISH(2, "发行"),
    CLOSED(3, "下架");


    @EnumValue
    private Integer key;

    @JsonValue
    private String display;
}
