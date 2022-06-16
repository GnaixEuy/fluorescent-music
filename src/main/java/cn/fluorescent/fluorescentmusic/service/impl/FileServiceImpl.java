package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.dao.FileDao;
import cn.fluorescent.fluorescentmusic.entity.File;
import cn.fluorescent.fluorescentmusic.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/16
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements FileService {

    private FileDao fileDao;

    /**
     * @param file
     * @return
     */
    @Override
    public Boolean insertWithCreatedByAndUpdatedBy(File file) {
        Date date = new Date();
        file.setCreatedTime(date);
        file.setUpdatedTime(date);
        return this.fileDao.insertWithCreatedByAndUpdatedBy(file) == 1;
    }

    @Autowired
    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }
}
