package cn.edu.ncu.ncublog.async.handler;

import cn.edu.ncu.ncublog.async.EventHandler;
import cn.edu.ncu.ncublog.async.EventModel;
import cn.edu.ncu.ncublog.async.EventType;
import cn.edu.ncu.ncublog.model.Message;
import cn.edu.ncu.ncublog.model.Status;
import cn.edu.ncu.ncublog.service.MessageService;
import cn.edu.ncu.ncublog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-04 10:40:40
 **/
@Component
public class FollowHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(FollowHandler.class);
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public void doHandler(EventModel eventModel) {
        try {

            if (eventModel.getActorId() == eventModel.getEntityOwnerId()) {
                return;
            }
            Message message = new Message();
            message.setFromId(Status.SYSTEM_USER_ID);
            message.setToId(eventModel.getEntityOwnerId());
            message.setCreatedDate(LocalDateTime.now());
            String content = "<a href='/user/" +
                    eventModel.getActorId() + "'>" +
                    userService.getUserById(eventModel.getActorId()).getName()
                    + "</a>&nbsp;&nbsp;关注了你";
            message.setContent(content);
            int i = messageService.addMessage(message);
            if (i > 0) {
                logger.info("消费关注信息事件成功...");
            } else {
                logger.error("消费关注信息事件失败...");
            }

        } catch (Exception e) {
            logger.error("消费关注信息事件失败...");
        }
    }

    @Override
    public List<EventType> getInteresEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
