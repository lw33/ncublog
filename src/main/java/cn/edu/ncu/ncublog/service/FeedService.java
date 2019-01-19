package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.dao.FeedDao;
import cn.edu.ncu.ncublog.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-04 14:01:37
 **/
@Service
public class FeedService {

    @Autowired
    private FeedDao feedDao;

    public int addFeed(Feed feed) {
        return feedDao.addFeed(feed);
    }

    public List<Feed> getLatestFeedsByUserIds(List<Integer> userIds, int offset, int limit) {

        return feedDao.selectLatestFeedsByUserIds(userIds, offset, limit);
    }

    public Feed getFeedById(int id) {
        return feedDao.selectFeedById(id);
    }

}
