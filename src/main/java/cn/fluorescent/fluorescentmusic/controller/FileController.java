package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.dto.file.FileUploadRequest;
import cn.fluorescent.fluorescentmusic.mapper.file.FileMapper;
import cn.fluorescent.fluorescentmusic.mapper.file.FileUploadMapper;
import cn.fluorescent.fluorescentmusic.service.FileService;
import cn.fluorescent.fluorescentmusic.vo.file.FileUploadVo;
import cn.fluorescent.fluorescentmusic.vo.file.FileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
@RestController
//@RolesAllowed(value = {"ROLE_ADMIN"})
@RequestMapping(value = {"/files"})
@RequiredArgsConstructor(onConstructor_ = {@Lazy, @Autowired})
public class FileController {

    private final FileService fileService;
    private final FileMapper fileMapper;
    private final FileUploadMapper fileUploadMapper;

    @PostMapping("/upload_init")
    public FileUploadVo initUpload(@Validated @RequestBody FileUploadRequest fileUploadRequest) throws IOException {
        return fileUploadMapper.toVo(fileService.initUpload(fileUploadRequest));
    }

    @PostMapping("/{id}/upload_finish")
    public FileVo finishUpload(@PathVariable String id) {
        return fileMapper.toVo(fileService.finishUpload(id));
    }

}