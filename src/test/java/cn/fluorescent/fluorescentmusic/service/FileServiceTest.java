package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */

@SpringBootTest
@Slf4j
@Transactional
class FileServiceTest extends BaseTest {

    private FileService fileService;
    
    @Autowired
    private void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

}
