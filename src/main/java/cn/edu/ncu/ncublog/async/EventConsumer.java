package cn.edu.ncu.ncublog.async;

import cn.edu.ncu.ncublog.util.RedisKey;
import cn.edu.ncu.ncublog.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author lw
 * @Date 2018-09-03 17:48:05
 **/

@Service
public class EventConsumer implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> beansOfType = applicationContext.getBeansOfType(EventHandler.class);
        if (beansOfType != null) {

            beansOfType.forEach((handlerName, handler) -> {

                List<EventType> interesEventTypes = handler.getInteresEventTypes();
                interesEventTypes.forEach(eventType -> {
                    if (!config.containsKey(eventType)) {
                        config.put(eventType, new ArrayList<>());
                    }
                    config.get(eventType).add(handler);
                });

            });
        }

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            while (true) {

                try {

                    String brpop = redisUtil.brpop(0, RedisKey.EVENTQUEUE);
                    EventModel eventModel = JSON.parseObject(brpop, EventModel.class);
                    if (!config.containsKey(eventModel.getEventType())) {
                        continue;
                    }
                    List<EventHandler> eventHandlers = config.get(eventModel.getEventType());
                    eventHandlers.forEach(eventHandler -> {
                        eventHandler.doHandler(eventModel);
                    });
                } catch (Exception e) {
                    logger.error("消费失败...", e.getMessage());
                }
            }

        });

    }
}
