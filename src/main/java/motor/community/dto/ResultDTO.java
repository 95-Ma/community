package motor.community.dto;

import lombok.Data;
import motor.community.exception.CustomizeErrorCode;
import motor.community.exception.CustomizeException;

import java.lang.reflect.Member;

/**
 * 响应传输对象
 *
 * @author motor
 * @create 2021-01-10-16:39
 */
@Data
public class ResultDTO {
    // 状态码
    private Integer code;
    // 响应信息
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorcode) {
        return errorOf(errorcode.getCode(), errorcode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }


}
