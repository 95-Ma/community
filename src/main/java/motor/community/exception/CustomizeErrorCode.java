package motor.community.exception;

/**
 * 错误码枚举类
 *
 * @author motor
 * @create 2021-01-09-20:35
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "该问题好像不见了，换个问题试试吧~"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或回复进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004, "服务有点小问题，稍后再来试试吧~"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在"),
    COMMENT_NOT_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "权限不足！"),
    NOTIFICATION_NOT_FOUND(2009, "该通知好像不见了~"),
    FILE_NOT_FOUND(2010, "上传的文件不见啦"),
    UPLOAD_ERROR(2011, "文件上传出现了一点小问题，请稍后再试"),
    ;

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
