package motor.community.Exception;

/**
 * 错误码枚举类
 *
 * @author motor
 * @create 2021-01-09-20:35
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("该问题好像不见了，换个问题试试吧~");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
