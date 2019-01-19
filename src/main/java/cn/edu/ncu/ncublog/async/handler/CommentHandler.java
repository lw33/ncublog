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
 * @Date 2018-09-04 17:56:50
 **/
@Component
public class CommentHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(CommentHandler.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


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
                    + "</a>&nbsp;&nbsp;评论了你的<a href='/blog/" +
                    eventModel.getEntityId()
                    + "'>博客</a>";
            message.setContent(content);
            int i = messageService.addMessage(message);
            if (i > 0) {
                logger.info("消费评论信息事件成功...");
            } else {
                logger.error("消费评论信息事件失败...");
            }

        } catch (Exception e) {
            logger.error("消费评论信息事件失败...", e.getMessage());
        }
    }

    @Override
    public List<EventType> getInteresEventTypes() {
        return Arrays.asList(EventType.COMMENT);
    }
}
