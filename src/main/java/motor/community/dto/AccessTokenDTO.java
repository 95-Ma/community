package motor.community.dto;

/**
 * Token访问数据传输对象
 *
 * @author motor
 * @create 2021-01-02-14:16
 */
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

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
