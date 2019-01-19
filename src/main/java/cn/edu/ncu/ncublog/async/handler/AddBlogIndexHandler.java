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
 * @Date 2018-09-05 01:19:37
 **/
@Component
public class AddBlogIndexHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddBlogIndexHandler.class);


    @Autowired
    private SearchService searchService;

    @Override
    public void doHandler(EventModel eventModel) {
        try {
            boolean b = searchService.addBlogIndex(
                    eventModel.get("blogId"),
                    eventModel.get("blogTitle"),
                    eventModel.get("blogContent")
            );
            if (b) {

                logger.info("博客添加索引成功...");

            } else {

                logger.info("博客添加索引失败...");
            }
        } catch (Exception e) {
            logger.info("博客添加索引失败...", e.getMessage());
        }

    }

    @Override
    public List<EventType> getInteresEventTypes() {
        return Arrays.asList(EventType.ADD_BLOG);
    }
}
