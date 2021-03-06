package cn.fluorescent.fluorescentmusic.entity;

import cn.fluorescent.fluorescentmusic.enmus.MusicStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 歌曲表
 * @author gnaixeuy
 * @TableName music
 */
@TableName(value = "music", resultMap = "musicResultMap")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Music extends BaseEntity implements Serializable {

    /**
     * 歌曲名
     */
    private String name;

    private String type;

    /**
     * 歌曲简介
     */
    private String description;

    /**
     * 歌曲图片
     */
    private String imageUrl;

    /**
     * 音乐文件ID
     */
    private String fileId;

    /**
     * 歌曲上架状态，1-DRAFT-草稿，2-PUBLISHED-已上架，3-CLOSED-已下架
     */
    private MusicStatus status;

}