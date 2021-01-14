package motor.community.controller;

import motor.community.dto.CommentCreateDTO;
import motor.community.dto.CommentDTO;
import motor.community.dto.ResultDTO;
import motor.community.enums.CommentTypeEnum;
import motor.community.enums.NotificationEnum;
import motor.community.exception.CustomizeErrorCode;
import motor.community.model.Comment;
import motor.community.model.User;
import motor.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 回复页面控制器
 *
 * @author motor
 * @create 2021-01-10-15:09
 */
@Controller
public class CommentController {


    @Resource
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        // 登录状态判断
        if (user == null) {
            // 返回错误信息
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        // 回复内容非空判断
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            // 返回错误信息
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_NOT_EMPTY);
        }
        // 将回复内容数据持久化
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDTO, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment, user);

        // 返回成功信息
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(value = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
