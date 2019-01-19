package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.util.RedisKey;
import cn.edu.ncu.ncublog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author lw
 * @Date 2018-09-03 21:48:08
 **/
@Service
@Transactional
public class FollowService {

    @Autowired
    private RedisUtil redisUtil;

    public boolean follow(int userId, int entityType, int entityId) {

        // 粉丝
        String followerKey = RedisKey.getFellowerKey(entityType, entityId);
        // 关注
        String followeeKey = RedisKey.getFelloweeKey(entityType, userId);

        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));

        return redisUtil.zadd(followerKey, second, String.valueOf(userId)) &&
                redisUtil.zadd(followeeKey, second, String.valueOf(entityId));
    }

    public boolean unfollow(int userId, int entityType, int entityId) {

        // 粉丝
        String followerKey = RedisKey.getFellowerKey(entityType, entityId);
        // 关注
        String followeeKey = RedisKey.getFelloweeKey(entityType, userId);


        return redisUtil.zrem(followerKey, String.valueOf(userId)) > 0 &&
                redisUtil.zrem(followeeKey, String.valueOf(entityId)) > 0;
    }

    public List<Integer> getFollowersId(int entityType, int entityId, int offset, int count) {
        // 粉丝
        String followerKey = RedisKey.getFellowerKey(entityType, entityId);

        return set2list(redisUtil.zrevrange(followerKey, offset, count));

    }

    public List<Integer> getFolloweesId(int entityType, int userId, int offset, int count) {
        // 关注
        String followeeKey = RedisKey.getFelloweeKey(entityType, userId);

        return set2list(redisUtil.zrevrange(followeeKey, offset, count));

    }

    public long getFollowerCount(int entityType, int entityId) {
        // 粉丝
        String followerKey = RedisKey.getFellowerKey(entityType, entityId);

        return redisUtil.zcard(followerKey);

    }

    public long getFolloweeCount(int userId, int entityType) {
        // 关注
        String followeeKey = RedisKey.getFelloweeKey(entityType, userId);

        return redisUtil.zcard(followeeKey);
    }

    public boolean isFollower(int userId, int entityType, int entityId) {
        // 粉丝
        String followerKey = RedisKey.getFellowerKey(entityType, entityId);
        return redisUtil.zscore(followerKey, String.valueOf(userId)) == null ? false : true;
    }

    private List<Integer> set2list(Set<String> set) {
        List<Integer> list = new ArrayList<>();
        set.forEach(integer -> {

            list.add(Integer.valueOf(integer));
        });
        return list;
    }

}
