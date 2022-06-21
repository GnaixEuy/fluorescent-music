package cn.fluorescent.fluorescentmusic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/22
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtistUpdateRequest {

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
    private String status;


}
