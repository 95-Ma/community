package motor.community.controller;

import motor.community.dto.FileDTO;
import motor.community.exception.CustomizeErrorCode;
import motor.community.exception.CustomizeException;
import motor.community.provider.QiNiuProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 文件上传控制类
 *
 * @author motor
 * @create 2021-01-13-16:11
 */
@Controller
public class FileController {

    @Resource
    private QiNiuProvider qiNiuProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        try {
            String upload = qiNiuProvider.upload(file.getInputStream(), file.getOriginalFilename());
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(upload);
            return fileDTO;
        } catch (IOException e) {
            throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
        }
    }
}
