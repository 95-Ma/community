package motor.community.mapper;

import motor.community.dto.QuestionQueryDTO;
import motor.community.model.Question;

import java.util.List;

/**
 * @author motor
 * @create 2021-01-09-21:40
 */
public interface QuestionExtMapper {
    int incView(Question record);

    int incComment(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
