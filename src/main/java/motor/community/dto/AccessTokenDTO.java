package motor.community.dto;

import lombok.Data;

/**
 * Token访问数据传输对象
 *
 * @author motor
 * @create 2021-01-02-14:16
 */
@Data
public class AccessTokenDTO {
    // 客户端id
    private String client_id;
    // 客户端密钥
    private String client_secret;
    // 携带 code
    private String code;
    // 重定向地址
    private String redirect_uri;
    // 服务器状态
    private String state;
}
