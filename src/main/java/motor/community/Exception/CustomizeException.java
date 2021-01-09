package motor.community.Exception;

/**
 * 自定义异常类
 *
 * @author motor
 * @create 2021-01-09-20:15
 */
public class CustomizeException extends RuntimeException {
    // 异常信息
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
}
