package cn.fluorescent.fluorescentmusic.service.impl;

import cn.fluorescent.fluorescentmusic.dao.FileDao;
import cn.fluorescent.fluorescentmusic.dto.file.FileDto;
import cn.fluorescent.fluorescentmusic.dto.file.FileUploadDto;
import cn.fluorescent.fluorescentmusic.dto.file.FileUploadRequest;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmus.FileStatus;
import cn.fluorescent.fluorescentmusic.enmus.Storage;
import cn.fluorescent.fluorescentmusic.entity.File;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.mapper.file.FileMapper;
import cn.fluorescent.fluorescentmusic.service.FileService;
import cn.fluorescent.fluorescentmusic.service.StorageService;
import cn.fluorescent.fluorescentmusic.utils.FileTypeTransformer;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/14
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Service
@Builder
@RequiredArgsConstructor(onConstructor_ = {@Lazy, @Autowired})
public class FileServiceImpl extends BaseService implements FileService {

    private final Map<String, StorageService> storageServiceMap;
    private final FileDao fileDao;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public FileUploadDto initUpload(FileUploadRequest fileUploadRequest) throws IOException {
        // 创建File 实体
        final File file = this.fileMapper.createEntity(fileUploadRequest);
        file.setType(FileTypeTransformer.getFileTypeFromExt(fileUploadRequest.getExt()));
        file.setStorage(getDefaultStorage());
        file.setCreatedBy(getCurrentUserEntity());
        file.setUpdatedBy(getCurrentUserEntity());
        final int result = this.fileDao.insert(file);
        if (result == 1) {
            final String key = fileUploadRequest.getKey();
            final String name = fileUploadRequest.getName();
            final File resultFile = this.fileDao.selectOne(
                    Wrappers.<File>lambdaQuery()
                            .eq(File::getKey, key)
                            .eq(File::getName, name));
            // 通过接口获取STS令牌
            final FileUploadDto fileUploadDto = this.storageServiceMap.get(getDefaultStorage().name()).initFileUpload();
            fileUploadDto.setKey(resultFile.getKey());
            fileUploadDto.setFileId(resultFile.getId());
            return fileUploadDto;
        } else {
            throw new BizException(ExceptionType.INNER_ERROR);
        }
    }

    @Override
    public FileDto finishUpload(String id) {
        final File file = this.getFileEntity(id);
        if (!Objects.equals(file.getCreatedBy().getId(), getCurrentUserEntity().getId())) {
            throw new BizException(ExceptionType.FILE_NOT_PERMISSION);
        }
        //TODO: 验证远程文件是否存在
        file.setFileStatus(FileStatus.UPLOADED);
        return this.fileMapper.toDto(file);
    }


    /**
     * TODO: 后台设置当前Storage
     */
    @Override
    public Storage getDefaultStorage() {
        return Storage.COS;
    }

    @Override
    public File getFileEntity(String id) {
        final File file = this.fileDao.selectById(id);
        if (ObjectUtil.isNull(file)) {
            throw new BizException(ExceptionType.FILE_NOT_FOUND);
        }
        return file;
    }

}

