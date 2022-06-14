package cn.fluorescent.fluorescentmusic.entity;

import cn.fluorescent.fluorescentmusic.enmus.MusicStatus;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 歌曲表
 * @author gnaixeuy
 * @TableName music
 */
@TableName(value ="music")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Music extends BaseEntity implements Serializable {

    /**
     * 歌曲名
     */
    private String name;

    /**
     * 歌曲简介
     */
    private String description;

    /**
     * 音乐文件ID
     */
    private String fileId;

    /**
     * 歌曲上架状态，1-DRAFT-草稿，2-PUBLISHED-已上架，3-CLOSED-已下架
     */
    private MusicStatus status;

}