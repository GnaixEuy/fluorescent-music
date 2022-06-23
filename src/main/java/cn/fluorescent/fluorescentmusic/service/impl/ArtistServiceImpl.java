package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.dao.ArtistDao;
import cn.fluorescent.fluorescentmusic.entity.Artist;
import cn.fluorescent.fluorescentmusic.service.ArtistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/21
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Service
public class ArtistServiceImpl extends ServiceImpl<ArtistDao, Artist> implements ArtistService {

    private ArtistDao artistDao;

    /**
     * @param artist
     * @return
     */
    @Override
    public boolean savaArtist(Artist artist) {
        return this.artistDao.registeredArtist(artist) == 1;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Artist findArtistByUserId(String userId) {
        Artist oneByCreatedBy = this.artistDao.findOneByCreatedBy(userId);
        if (oneByCreatedBy == null) {
            return null;
        }
        return this.getById(oneByCreatedBy.getId());
    }

    @Autowired
    public void setArtistDao(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
}
