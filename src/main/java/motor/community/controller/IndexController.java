package motor.community.controller;

import com.zaxxer.hikari.HikariJNDIFactory;
import motor.community.mapper.UserMapper;
import motor.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author motor
 * @create 2021-01-01-19:59
 */
@Controller
public class IndexController {

    @Resource
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }
        return "index";
    }
}
