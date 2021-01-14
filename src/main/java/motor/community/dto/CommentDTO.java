package motor.community.dto;

import lombok.Data;
import motor.community.model.User;

/**
 * 回复数据传输对象
 *
 * @author motor
 * @create 2021-01-11-15:26
 */
@Data
public class CommentDTO {
    // 回复id
    private Long id;
    // 回复用户id
    private Long commentator;
    // 创建时间
    private Long gmtCreate;
    // 修改时间
    private Long gmtModified;
    // 点赞数
    private Long likeCount;
    // 回复问题的id
    private Long parentId;
    // 回复类型
    private Integer type;
    // 回复内容
    private String content;
    // 回复的用户信息
    private User user;
    // 二级回复数
    private Integer commentCount;
}
