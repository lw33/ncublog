package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.model.Status;
import cn.edu.ncu.ncublog.util.RedisKey;
import cn.edu.ncu.ncublog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lw
 * @Date 2018-09-03 15:48:05
 **/

@Service
public class LikeService {

    @Autowired
    private RedisUtil redisUtil;


    public long like(int userId, int entityType, int entityId) {

        String likeKey = RedisKey.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKey.getDisLikeKey(entityType, entityId);

        redisUtil.sadd(likeKey, String.valueOf(userId));
        redisUtil.srem(dislikeKey, String.valueOf(userId));

        return redisUtil.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId) {

        String dislikeKey = RedisKey.getDisLikeKey(entityType, entityId);
        String likeKey = RedisKey.getLikeKey(entityType, entityId);

        redisUtil.sadd(dislikeKey, String.valueOf(userId));
        redisUtil.srem(likeKey, String.valueOf(userId));

        return redisUtil.scard(likeKey);
    }

    public long getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKey.getLikeKey(entityType, entityId);
        String dislikeKey = RedisKey.getDisLikeKey(entityType, entityId);

        if (redisUtil.sismember(likeKey, String.valueOf(userId))) {
            return Status.LIKE;
        } else if (redisUtil.sismember(dislikeKey, String.valueOf(userId))) {
            return Status.DISLIKE;
        } else {
            return Status.NOFEEL;
        }
    }


    public long getLikeCount(int entityType, int entityId) {
        return redisUtil.scard(RedisKey.getLikeKey(entityType, entityId));
    }
}
