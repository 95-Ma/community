package motor.community.exception;

/**
 * 自定义异常类
 *
 * @author motor
 * @create 2021-01-09-20:15
 */
public class CustomizeException extends RuntimeException {
    // 异常信息
    private String message;
    // 异常码
    private Integer code;


    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }
}
