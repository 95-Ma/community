package motor.community.dto;

import lombok.Data;

/**
 * @author motor
 * @create 2021-01-14-16:05
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
