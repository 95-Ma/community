package motor.community.enums;

/**
 * @author motor
 * @create 2021-01-12-15:36
 */
public enum NotificationTypeEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationTypeEnum(int status) {
        this.status = status;
    }
}
