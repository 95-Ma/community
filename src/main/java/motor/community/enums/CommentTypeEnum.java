package motor.community.enums;

/**
 * 回复类型枚举类
 *
 * @author motor
 * @create 2021-01-10-16:53
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;


    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType() == type) return true;
        }
        return false;
    }
}
