package motor.community.provider;

import com.alibaba.fastjson.JSON;
import motor.community.dto.AccessTokenDTO;
import motor.community.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Github支持类 负责与github的 http请求和数据交互
 *
 * @author motor
 * @tips 使用okHttp工具和fastjson工具对配置请求类型以及请求数据的格式转换
 * @create 2021-01-02-14:15
 */
@Component
public class GithubProvider {
    /**
     * 使用post方法获取token信息
     * @param accessTokenDTO Token访问数据传输对象
     * @return token Github封装的token信息
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        // 定义请求数据格式为json，字符集为utf-8
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        // 创建okhttp代理客户端
        OkHttpClient client = new OkHttpClient();

        // 构建请求体
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        // 构建请求
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        // 执行请求并对响应信息进行字符串分割，获取并返回令牌信息
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如若请求失败，则返回null
        return null;
    }

    /**
     * 使用get方法获取用户信息并封装到GithubUserDTO对象
     *
     * @param accessToken 令牌信息
     * @return 获取的用户信息
     */
    public GithubUserDTO getUser(String accessToken) {
        // 创建okhttp代理客户端
        OkHttpClient client = new OkHttpClient();
        // 构建请求
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .build();
        // 执行请求，获取响应信息，使用fastjson解析为GtihubUserDTO对象并注入属性，最终返回对象
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUserDTO githubUserDTO = JSON.parseObject(string, GithubUserDTO.class);
            return githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如若请求失败，返回null
        return null;
    }
}
