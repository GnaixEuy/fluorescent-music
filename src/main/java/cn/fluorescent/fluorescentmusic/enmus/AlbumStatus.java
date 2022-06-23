package cn.fluorescent.fluorescentmusic.enmus;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AlbumStatus {
    DRAFT(1, "草稿"),
    PUBLISHED(2, "已上架"),
    CLOSED(3, "已下架");


    @EnumValue
    private Integer key;

    @JsonValue
    private String display;
}
