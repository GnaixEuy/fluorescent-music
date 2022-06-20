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
 * @date 2022/6/19
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PlayListStatus {
    /**
     * 1-DRAFT-草稿，2-PUBLISHED-已上架，3-CLOSED-已下架
     */
    DRAFT(1, "草稿"),
    PUBLISHED(2, "已上架"),
    CLOSED(3, "已下架");


    @EnumValue
    private Integer key;

    @JsonValue
    private String display;
}
