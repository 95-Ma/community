package motor.community.mapper;

import motor.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * 根据传入的offset和size获取特定数据并封装到question集合
     *
     * @param offset 偏移位置
     * @param size   偏移量
     * @return question集合
     */
    @Select(value = "select * from question limit #{offset},#{size}")
    List<Question> getAllQuestion(@Param("offset") Integer offset, @Param("size") Integer size);

    /**
     * 查询问题列表总记录数
     *
     * @return 问题列表总记录数
     */
    @Select("select count(1) from question")
    Integer questionCount();
}
