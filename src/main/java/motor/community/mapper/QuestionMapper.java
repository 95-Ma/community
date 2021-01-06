package motor.community.mapper;

import motor.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * question持久化层接口
 *
 * @author motor
 * @create 2021-01-05-14:55
 */
@Mapper
public interface QuestionMapper {

    /**
     * 插入一条数据到question对象
     *
     * @param question
     */
    @Insert(value = "insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    /**
     * 获取所有数据并封装到question集合
     *
     * @return question集合
     */
    @Select(value = "select * from question")
    List<Question> getAllQuestion();
}
