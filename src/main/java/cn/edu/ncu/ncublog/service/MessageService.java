package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.dao.MessageDao;
import cn.edu.ncu.ncublog.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-02 20:35:22
 **/
@Service
@Transactional
public class MessageService {

    @Autowired
    MessageDao messageDao;

    public int addMessage(Message message) {
        return messageDao.addMessage(message) > 0 ? message.getId() : 0;
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDao.selectConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDao.selectConversationUnreadCount(userId, conversationId);
    }

    public int setRead(int userId, String conversationId) {
        return messageDao.updateReadStatus(userId, conversationId);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDao.selectCoversationDetail(conversationId, offset, limit);
    }
}
