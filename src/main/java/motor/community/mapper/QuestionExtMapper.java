package motor.community.mapper;

import motor.community.model.Question;

/**
 * @author motor
 * @create 2021-01-09-21:40
 */
public interface QuestionExtMapper {
    int incView(Question record);

    int incComment(Question record);
}
