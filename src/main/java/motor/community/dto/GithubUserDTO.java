package motor.community.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Insert;

/**
 * 负责接收Github封装回传的用户信息
 *
 * @author motor
 * @create 2021-01-02-14:54
 */
@Data
public class GithubUserDTO {
    // 用户昵称
    private String name;
    // 用户id
    private Long id;
    // 个人简介
    private String bio;
    // 头像地址
    private String avatar_url;
}
