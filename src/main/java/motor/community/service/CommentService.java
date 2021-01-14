package motor.community.service;

import motor.community.dto.CommentDTO;
import motor.community.enums.CommentTypeEnum;
import motor.community.enums.NotificationEnum;
import motor.community.enums.NotificationTypeEnum;
import motor.community.exception.CustomizeErrorCode;
import motor.community.exception.CustomizeException;
import motor.community.mapper.*;
import motor.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author motor
 * @create 2021-01-10-16:56
 */
@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionExtMapper questionExtMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentExtMapper commentExtMapper;

    @Resource
    private NotificationMapper notificationMapper;

    /**
     * 添加回复到回复表
     */
    @Transactional
    public void insert(Comment comment, User commentator) {
        // 如果问题已经被删除时做异常处理
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        // 回复等级非空判断
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            // 为一级回复添加回复数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incComment(parentComment);
            // 创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_COMMENT, question.getId());

        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
            // 创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationEnum notificationType, Long outerId) {
        if (receiver == comment.getCommentator()) {
            return;
        }
        // 为评论添加通知功能
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationTypeEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setOuterTitle(outerTitle);
        notification.setNotifierName(notifierName);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        // 根据回复类型和问题id查找回复列表
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        // 返回的回复列表根据创建时间倒序排序
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);

        // 如果没有回复则返回空列表
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取回复列表中的用户id列表
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        // 根据用户id查询所有的用户列表
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        // 将用户id和用户信息转换为Map集合
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 将comment 转换为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
