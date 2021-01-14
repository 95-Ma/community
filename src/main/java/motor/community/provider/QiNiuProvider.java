package motor.community.provider;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import motor.community.exception.CustomizeErrorCode;
import motor.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author motor
 * @create 2021-01-13-17:14
 */
@Service
public class QiNiuProvider {

    @Value("${qiniu.account.accesskey}")
    private String accessKey;

    @Value("${qiniu.account.secretkey}")
    private String secretKey;

    @Value("${qiniu.bucket.name}")
    private String bucketName;

    @Value("${qiniu.domain}")
    private String domain;


    /**
     * @param fileStream 字节输入流
     * @param fileName   文件名
     * @return
     */
    public String upload(InputStream fileStream, String fileName) {
        // 为上传到七牛云空间的文件命名
        String key = "";
        String[] fileSpliter = fileName.split("\\.");
        if (fileSpliter.length > 1) {
            key = UUID.randomUUID().toString() + "." + fileSpliter[fileSpliter.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
        }

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucketName);

        try {
            //构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.region2());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            Response response = uploadManager.put(fileStream, key, upToken, null, null);
            if (!response.isOK()) {
                throw new CustomizeException(CustomizeErrorCode.UPLOAD_ERROR);
            }

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            String path = domain + "/" + putRet.key;
            return path;
        } catch (QiniuException ex) {
            throw new CustomizeException(CustomizeErrorCode.FILE_NOT_FOUND);
        }

    }

}
