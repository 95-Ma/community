package motor.community.controller;

import motor.community.dto.PaginationDTO;
import motor.community.model.User;
import motor.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 个人中心控制器
 *
 * @author motor
 * @create 2021-01-06-19:37
 */
@Controller
public class ProfileController {


    @Resource
    private QuestionService questionService;

    /**
     * 个人中心控制器
     *
     * @param action 个人中心显示内容的分类
     * @param model
     * @return 个人中心页面
     */
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(value = "action") String action,
                          Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size) {

        // 获取Session域中的用户信息
        User user = (User) request.getSession().getAttribute("user");

        // 如果用户未登录，重定向回首页
        if (user == null) {
            return "redirect:/";
        }


        // 对根据传入地址栏占位符获取的参数进行等值判断并往Request域中放入section和sectionName
        if ("questions".contains(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".contains(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        // 根据用户id、页码和每页条数获取问题列表
        PaginationDTO paginationDTO = questionService.getAllQuestionDTO(user.getId(), page, size);
        // 将返回的paginationDTO对象放入Request域中
        model.addAttribute("paginationDTO", paginationDTO);
        return "profile";
    }
}
