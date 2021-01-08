package motor.community.interceptor;

import motor.community.mapper.UserMapper;
import motor.community.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session拦截器
 *
 * @author motor
 * @create 2021-01-08-16:48
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前cookies集合
        Cookie[] cookies = request.getCookies();
        // 非空判断
        if (cookies != null && cookies.length != 0) {
            // 遍历并获取key为token的cookie，再根据token查找用户并将用户存放到Session域中
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
