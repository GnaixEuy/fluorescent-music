package cn.fluorescent.fluorescentmusic.controller;

import cn.fluorescent.fluorescentmusic.config.SecurityConfig;
import cn.fluorescent.fluorescentmusic.config.TencentCosConfig;
import cn.fluorescent.fluorescentmusic.enmus.ExceptionType;
import cn.fluorescent.fluorescentmusic.enmus.FileStatus;
import cn.fluorescent.fluorescentmusic.enmus.Storage;
import cn.fluorescent.fluorescentmusic.entity.TencentCos;
import cn.fluorescent.fluorescentmusic.entity.User;
import cn.fluorescent.fluorescentmusic.exception.BizException;
import cn.fluorescent.fluorescentmusic.service.FileService;
import cn.fluorescent.fluorescentmusic.service.UserService;
import cn.fluorescent.fluorescentmusic.utils.CosUploadUtil;
import cn.fluorescent.fluorescentmusic.utils.FileTypeTransformer;
import cn.fluorescent.fluorescentmusic.utils.KsuidIdentifierGenerator;
import cn.fluorescent.fluorescentmusic.vo.ResponseResult;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
@Api(tags = {"文件接口"})
//@RolesAllowed(value = {"ROLE_ADMIN"})
@RequestMapping(value = {"/files"})
@Log4j2
public class FileController {

    @Value("${tencent.cos.bucket-name}")
    private String bucketName;
    private CosUploadUtil cosUploadUtil;
    private TencentCos tencentCos;
    private TencentCosConfig tencentCosConfig;
    private KsuidIdentifierGenerator ksuidIdentifierGenerator;
    private UserService userService;

    private FileService fileService;

    @PostMapping(value = "/uploadFileTencentCos")
    public ResponseResult<HashMap<String, Object>> getUploadFileTencentCosUrl(@RequestParam("multipartFile") MultipartFile multipartFile, HttpServletRequest request) throws SystemException {
        //判断文件不为空
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.getSize() <= 0) {
            throw new BizException(ExceptionType.FILE_NOT_FOUND);
        }
        File localFile;
        String originalFilename;
        String[] filename;
        try {
            originalFilename = multipartFile.getOriginalFilename();
            log.info("fileName = {}", originalFilename);
            assert originalFilename != null;
            filename = originalFilename.split("\\.");
            localFile = File.createTempFile(filename[0], filename[1]);
            //将localFile这个文件所指向的文件  上传到对应的目录
            multipartFile.transferTo(localFile);
            localFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("MultipartFile transfer file IOException ={}", e.getMessage());
            //文件上传失败就返回错误响应
            throw new BizException(ExceptionType.FILE_UPLOAD_ERROR);
        }
        String fileNameUUID = this.ksuidIdentifierGenerator.nextUUID(multipartFile);
        System.out.println(fileNameUUID);
        String filePath = TencentCosConfig.COS_ATTACHMENT + "/" + fileNameUUID + "." + filename[1];
        ResponseResult<HashMap<String, Object>> upload = cosUploadUtil.upload(bucketName, filePath, localFile, originalFilename);
        cn.fluorescent.fluorescentmusic.entity.File file = new cn.fluorescent.fluorescentmusic.entity.File(
                fileNameUUID,
                filePath,
                filename[1],
                multipartFile.getSize(),
                FileTypeTransformer.getFileTypeFromExt(filename[1]),
                Storage.COS,
                FileStatus.UPLOADED
        );
        file.setId(fileNameUUID);
        String header = request.getHeader(SecurityConfig.HEADER_STRING);
        String username = JWT.require(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()))
                .build()
                .verify(header.replace(SecurityConfig.TOKEN_PREFIX, ""))
                .getSubject();
        if (username != null) {
            User user = userService.loadUserByUsername(username);
            file.setCreatedBy(user);
            file.setUpdatedBy(user);
            boolean save = this.fileService.insertWithCreatedByAndUpdatedBy(file);
            if (!save) {
                throw new BizException(ExceptionType.FILE_NOT_FOUND);
            }
        }
        HashMap<String, Object> data = upload.getData();
        data.put("fileId", fileNameUUID);
        return upload;
    }

    @Autowired
    public void setTencentCos(TencentCos tencentCos) {
        this.tencentCos = tencentCos;
    }

    @Autowired
    public void setTencentCosConfig(TencentCosConfig tencentCosConfig) {
        this.tencentCosConfig = tencentCosConfig;
    }

    @Autowired
    public void setKsuidIdentifierGenerator(KsuidIdentifierGenerator ksuidIdentifierGenerator) {
        this.ksuidIdentifierGenerator = ksuidIdentifierGenerator;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setCosUploadUtil(CosUploadUtil cosUploadUtil) {
        this.cosUploadUtil = cosUploadUtil;
    }
}