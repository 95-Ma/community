package motor.community.servie;

import motor.community.mapper.UserMapper;
import motor.community.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户类业务逻辑
 *
 * @author motor
 * @create 2021-01-08-18:26
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;


    /**
     * 调用持久层接口判断用户是否已经创建，是的话更新Token，反之则创建
     *
     * @param user 用户信息
     */
    public void createOrUpdate(User user) {
        // 根据AccountId获取user
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        // 非空判断
        if (dbUser == null) {
            // 设置创建事件和修改时间
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            // 插入用户信息
            userMapper.insert(user);
        } else {
            // 更新可能变更的用户信息
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
