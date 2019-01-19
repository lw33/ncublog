package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.dao.CommentDao;
import cn.edu.ncu.ncublog.model.Comment;
import cn.edu.ncu.ncublog.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-02 16:31:53
 **/
@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDao.selectCommentsByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDao.addComment(comment) > 0 ? comment.getId() : 0;
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDao.selectcommentCountByEntity(entityId, entityType);
    }

    public boolean deleteComment(int id) {
        return commentDao.updateStatus(id, Status.INVALID) > 0;
    }

    public Comment getCommentById(int id) {
        return commentDao.selectCommentById(id);
    }

    public int getCommentCountByUserId(int userId) {

        return commentDao.selectcommentCountByUserId(userId);
    }

}
