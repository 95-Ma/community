package motor.community.dto;

import lombok.Data;
import motor.community.model.User;

/**
 * question数据传输对象
 *
 * @author motor
 * @create 2021-01-05-21:23
 */
@Data
public class QuestionDTO {
    // 序号
    private Long id;
    // 标题
    private String title;
    // 内容描述
    private String description;
    // 标签
    private String tag;
    // 创建时间
    private Long gmtCreate;
    // 修改时间
    private Long gmtModified;
    // 发起者
    private Long creator;
    // 阅读量
    private Integer viewCount;
    // 评论数
    private Integer commentCount;
    // 点赞数
    private Integer likeCount;
    // 发起者对应的用户信息
    private User user;
}
