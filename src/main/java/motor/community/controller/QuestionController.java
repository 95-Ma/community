package motor.community.controller;

import motor.community.dto.QuestionDTO;
import motor.community.mapper.QuestionMapper;
import motor.community.servie.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * 问题详情页控制器
 *
 * @author motor
 * @create 2021-01-08-17:29
 */
@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;

    /**
     * 根据传入id查找问题列表和相关的用户信息并存入Request域中
     *
     * @param id
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        // 调用业务逻辑层接口方法查询问题列表和用户信息
        QuestionDTO questionDTO = questionService.getById(id);
        // 存入Request域中
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
