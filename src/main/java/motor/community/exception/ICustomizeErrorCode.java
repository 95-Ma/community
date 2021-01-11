package motor.community.exception;

/**
 * 错误码接口
 *
 * @author motor
 * @create 2021-01-09-20:35
 */
public interface ICustomizeErrorCode {

    /**
     * 获取错误信息
     */
    String getMessage();

    /**
     * 获取错误码
     */
    Integer getCode();
}
