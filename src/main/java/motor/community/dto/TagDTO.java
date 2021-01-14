package motor.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author motor
 * @create 2021-01-11-21:20
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
