package cn.edu.ncu.ncublog.async;

/**
 * @Author lw
 * @Date 2018-09-03 16:33:43
 **/
public enum EventType {

    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3),
    FOLLOW(4),
    UNFOLLOW(5),
    ADD_BLOG(6),
    DELETE_BLOG(7);

    private int value;
    EventType(int value) { this.value = value; }
    public int getValue() { return value; }
}
