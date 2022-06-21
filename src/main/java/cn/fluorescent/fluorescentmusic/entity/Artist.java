package cn.fluorescent.fluorescentmusic.entity;

import cn.fluorescent.fluorescentmusic.enmus.GeneralStatus;
import cn.fluorescent.fluorescentmusic.vo.MusicVo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 歌手表
 *
 * @TableName artist
 */
@TableName(value = "artist", resultMap = "artistResultMap")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Artist extends BaseEntity implements Serializable {

    /**
     * 歌手名字
     */
    private String name;

    /**
     * 歌手备注
     */
    private String remark;

    /**
     * 歌手照片url
     */
    private String photoUrl;

    /**
     * 歌手上架状态，0-DRAFT-草稿，1-PUBLISHED-已上架，2-BLOCKED-已封禁
     */
    private GeneralStatus status = GeneralStatus.DRAFT;

    /**
     * 是否推荐：推荐：1； 不推荐：0；默认：0
     */
    private Integer recommended;

    /**
     * 推荐因数：越高越在上面
     */
    private Integer recommendFactor;


    /**
     * 创建人ID
     */
    @TableField(exist = false)
    private User createdBy;

    /**
     * 最后更新人ID
     */
    @TableField(exist = false)
    private User updatedBy;


    @TableField(exist = false)
    private List<MusicVo> musicList;

}