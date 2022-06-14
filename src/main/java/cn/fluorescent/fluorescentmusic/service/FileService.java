package cn.fluorescent.fluorescentmusic.service;

import cn.fluorescent.fluorescentmusic.dto.file.FileDto;
import cn.fluorescent.fluorescentmusic.dto.file.FileUploadDto;
import cn.fluorescent.fluorescentmusic.dto.file.FileUploadRequest;
import cn.fluorescent.fluorescentmusic.enmus.Storage;
import cn.fluorescent.fluorescentmusic.entity.File;

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
public interface FileService {

    FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException;

    FileDto finishUpload(String id);

    Storage getDefaultStorage();

    File getFileEntity(String id);

}
