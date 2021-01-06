package motor.community.model;

import lombok.Data;

/**
 * 论坛用户，封装用户信息
 *
 * @author motor
 * @create 2021-01-03-21:06
 */
@Data
public class User {
    // 序号
    private Integer id;
    // 昵称
    private String name;
    // 用户id
    private String accountId;
    // 用户访问令牌
    private String token;
    // 创建时间
    private Long gmtCreate;
    // 修改时间
    private Long gmtModified;
    // 用户头像地址
    private String avatarUrl;
}
