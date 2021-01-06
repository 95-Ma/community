package motor.community.model;

import lombok.Data;

/**
 * 问题对象，封装发起问题的相关信息
 *
 * @author motor
 * @create 2021-01-05-14:55
 */
@Data
public class Question {
    // 序号
    private int id;
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
    // 发起者，与User对象的id对应
    private Integer creator;
    // 阅读量
    private Integer viewCount;
    // 评论数
    private Integer commentCount;
    // 点赞数
    private Integer likeCount;

}
