package cn.edu.ncu.ncublog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author lw
 * @Date 2018/8/20 8:52
 **/
@Service
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void sadd(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public void srem(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    public long scard(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public boolean sismember(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public String brpop(int timeout, String key) {
        return redisTemplate.opsForList().rightPop(key, timeout, TimeUnit.SECONDS);
    }

    public long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public List<String> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public Boolean zadd(String key, double score, String value) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long zrem(String key, String value) {

        return redisTemplate.opsForZSet().remove(key, value);

    }

    public Set<String> zrange(String key, int start, int end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<String> zrevrange(String key, int start, int end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public long zcard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public Double zscore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }


}
