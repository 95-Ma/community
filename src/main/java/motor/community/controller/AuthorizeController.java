package motor.community.controller;

import lombok.extern.slf4j.Slf4j;
import motor.community.dto.GithubUserDTO;
import motor.community.model.User;
import motor.community.provider.GithubProvider;
import motor.community.dto.AccessTokenDTO;
import motor.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 获取Github授权控制器
 *
 * @author motor
 * @create 2021-01-02-14:08
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    // 服务器id
    @Value("${github.client.id}")
    private String clientId;
    // 服务器密钥
    @Value("${github.client.secret}")
    private String clientSecret;
    // 回调地址
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Resource
    private UserService userService;

    /**
     * 当访问回调地址时进入该控制器
     *
     * @param code     携带code
     * @param state    服务器状态
     * @param request  原生请求API
     * @param response 原生响应API
     * @return 不管成功失败都重定向到首页
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        // 配置访问令牌数据传输对象
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        // 获取访问令牌
        String tokenAccess = githubProvider.getAccessToken(accessTokenDTO);
        // 获取Github用户数据传输对象
        GithubUserDTO githubUserDTO = githubProvider.getUser(tokenAccess);
        // 判断Github用户数据传输对象获取是否成功
        if (githubUserDTO != null && githubUserDTO.getId() != null) {
            // 创建用户对象，并注入属性，最后将数据持久化
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            user.setAvatarUrl(githubUserDTO.getAvatar_url());
            // 判断用户是否创建或更新相关信息
            userService.createOrUpdate(user);
            // 登录成功，将随机生成的UUID写入cookie作为用户登录令牌
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            log.error("callback get github error,{}", githubUserDTO);
            // 登录失败，重新登录
            return "redirect:/";
        }
    }

    /**
     * 退出登录控制器，清除token的Cookie以及Session域中的user
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 移除Session域中的user
        request.getSession().removeAttribute("user");
        // 清除Cookie中的token
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
