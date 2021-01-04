package motor.community.mapper;

import motor.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author motor
 * @create 2021-01-03-20:58
 */
@Mapper
public interface UserMapper {
    @Insert(value = "insert into c_user (name,account_id,token,gmt_create,gmt_modified) " +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
