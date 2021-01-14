package motor.community.dto;

import lombok.Data;
import motor.community.model.User;

/**
 * 通知数据传输对象
 *
 * @author motor
 * @create 2021-01-12-16:01
 */
@Data
public class NotificationDTO {
    // id
    private Long id;
    // 创建时间
    private Long gmtCreate;
    // 处理状态
    private Integer status;
    // 通知者id
    private Long notifier;
    // 通知者名字
    private String notifierName;
    // 通知内容
    private String outerTitle;
    //
    private Long outerid;
    // 通知类型
    private Integer type;
    // 通知问题标题
    private String typeName;

}
