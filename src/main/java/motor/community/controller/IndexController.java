package motor.community.controller;

import motor.community.dto.PaginationDTO;
import motor.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author motor
 * @create 2021-01-01-19:59
 */
@Controller
public class IndexController {


    @Resource
    private QuestionService questionService;

    /**
     * 首页控制器
     *
     * @param request
     * @param model
     * @param page    页数
     * @param size    每页显示几条问题
     * @return 首页
     */
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "5") Integer size) {
        // 根据page和size返回页码DTO对象
        PaginationDTO pagination = questionService.getAllQuestionDTO(page, size);
        // 将页码DTO对象存放到Request域中
        model.addAttribute("pagination", pagination);
        // 返回首页
        return "index";
    }
}
