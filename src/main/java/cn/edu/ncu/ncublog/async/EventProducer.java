package cn.edu.ncu.ncublog.async;

import cn.edu.ncu.ncublog.util.RedisKey;
import cn.edu.ncu.ncublog.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lw
 * @Date 2018-09-03 17:39:53
 **/
@Service
public class EventProducer {

    @Autowired
    private RedisUtil redisUtil;

    public void fireHandler(EventModel eventModel) {
        String eventQueueKey = RedisKey.EVENTQUEUE;
        String s = JSON.toJSONString(eventModel);
        redisUtil.lpush(eventQueueKey, s);
    }
}
