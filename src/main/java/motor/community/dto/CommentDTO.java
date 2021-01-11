package motor.community.dto;

import lombok.Data;

/**
 * 回复数据传输对象
 *
 * @author motor
 * @create 2021-01-10-15:11
 */
@Data
public class CommentDTO {
    // 回复者id
    private Long parentId;
    // 回复内容
    private String content;
    // 回复等级类型
    private Integer type;
}
