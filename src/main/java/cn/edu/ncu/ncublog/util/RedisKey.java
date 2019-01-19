package cn.edu.ncu.ncublog.util;

/**
 * @Author lw
 * @Date 2018-09-03 15:44:40
 **/
public class RedisKey {

    private static final String SPLIT = "_";
    private static final String LIKE = "LIKE";
    private static final String DISLIKE = "DISLIKE";
    private static final String FELLOWER = "FELLOW";
    private static final String FELLOWEE = "FELLOWEE";

    public static final String EVENTQUEUE = "EVENTQUEUE";

    public static final String FEEDS = "FEEDS";



    public static String getLikeKey(int entityType, int entityId) {
        return LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getFellowerKey(int entityType, int entityId) {
        return FELLOWER  + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getFelloweeKey(int entityType, int entityId) {
        return FELLOWEE  + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getFeedsKey(int userId) {
        return FEEDS + SPLIT + userId;
    }
}
