package motor.community.mapper;

import motor.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


/**
 * 用户持久层接口
 *
 * @author motor
 * @create 2021-01-03-20:58
 */
@Mapper
public interface UserMapper {
    /**
     * 插入用户到c_user表
     *
     * @param user 用户
     */
    @Insert(value = "insert into c_user (name,account_id,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    /**
     * 根据token信息查询用户
     */
    @Select(value = "select * from c_user where token = #{token}")
    User findByToken(@Param(value = "token") String token);

    /**
     * 根据用户id查询用户
     */
    @Select(value = "select * from c_user where \"id\" = #{id}")
    User findById(@Param(value = "id") Integer id);

    /**
     * 根据Account_id查询用户
     */
    @Select(value = "select * from c_user where account_id = #{accountId}")
    User findByAccountId(@Param(value = "accountId") String accountId);

    @Update(value = "update c_user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where \"id\" = #{id}")
    void update(User dbUser);
}
