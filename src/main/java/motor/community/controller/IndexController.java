package motor.community.controller;

import com.zaxxer.hikari.HikariJNDIFactory;
import motor.community.dto.QuestionDTO;
import motor.community.mapper.QuestionMapper;
import motor.community.mapper.UserMapper;
import motor.community.model.Question;
import motor.community.model.User;
import motor.community.servie.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author motor
 * @create 2021-01-01-19:59
 */
@Controller
public class IndexController {

    @Resource
    UserMapper userMapper;

    @Resource
    QuestionService questionService;

    /**
     * 首页控制器
     *
     * @param request
     * @param model
     * @return 首页
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
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
        // 将问题列表存放到Request域中
        List<QuestionDTO> questionDTOList = questionService.getAllQuestionDTO();
        model.addAttribute("questions", questionDTOList);
        // 返回首页
        return "index";
    }
}
