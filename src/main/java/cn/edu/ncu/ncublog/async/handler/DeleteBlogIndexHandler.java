package cn.edu.ncu.ncublog.async.handler;

import cn.edu.ncu.ncublog.async.EventHandler;
import cn.edu.ncu.ncublog.async.EventModel;
import cn.edu.ncu.ncublog.async.EventType;
import cn.edu.ncu.ncublog.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-06 01:22:51
 **/

@Component
public class DeleteBlogIndexHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeleteBlogIndexHandler.class);

    @Autowired
    private SearchService searchService;

    @Override
    public void doHandler(EventModel eventModel) {

        try {
            boolean b = searchService.deleteBlogById(String.valueOf(eventModel.getEntityId()));
            if (b) {

                logger.info("删除博客索引成功...");

            } else {

                logger.info("删除博客索引失败...");
            }
        } catch (Exception e) {
            logger.error("删除博客索引失败");
        }

    }

    @Override
    public List<EventType> getInteresEventTypes() {
        return Arrays.asList(EventType.DELETE_BLOG);
    }
}
