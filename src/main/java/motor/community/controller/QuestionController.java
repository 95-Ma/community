package motor.community.controller;

import motor.community.dto.CommentDTO;
import motor.community.dto.QuestionDTO;
import motor.community.enums.CommentTypeEnum;
import motor.community.service.CommentService;
import motor.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private CommentService commentService;

    /**
     * 根据传入id查找问题列表和相关的用户信息并存入Request域中
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        // 调用业务逻辑层接口方法查询问题列表和用户信息
        QuestionDTO questionDTO = questionService.getById(id);

        // 调用业务逻辑层接口方法查询相关问题列表
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        // 调用业务逻辑层接口方法查询回复及其用户信息列表
        List<CommentDTO> commentCreateDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        // 累加阅读数
        questionService.incView(id);
        // 存入Request域中
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentCreateDTOList);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
