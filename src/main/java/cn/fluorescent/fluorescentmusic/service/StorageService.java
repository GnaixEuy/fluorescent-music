package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.dto.file.FileUploadDto;

import java.io.IOException;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
public interface StorageService {

    FileUploadDto initFileUpload() throws IOException;

    String getFileUri(String fileKey);

}
