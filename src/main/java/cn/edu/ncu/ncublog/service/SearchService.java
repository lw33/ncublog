package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.model.Blog;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-04 21:58:09
 **/

@Service
public class SearchService {

    @Autowired
    private SolrClient solrClient;

    private static final String COLLECTION = "ncublog";

    private static final String BLOG_TITLE_FIELD = "blog_title";

    private static final String BLOG_CONTENT_FIELD = "blog_content";

    public List<Blog> getBlogsByKeyword(String keyword, int offset, int count,
                                        String hlPre, String hlPos) throws Exception {

        SolrQuery query = new SolrQuery(keyword);
        query.setStart(offset);
        query.setRows(count);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl", BLOG_TITLE_FIELD + "," + BLOG_CONTENT_FIELD);

        QueryResponse response = solrClient.query(COLLECTION,query);

        List<Blog> blogs = new ArrayList<>();

        response.getHighlighting().forEach((id, highlightPart) -> {
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(id));
            blog.setContent(highlightPart.get(BLOG_CONTENT_FIELD) == null ? null : highlightPart.get(BLOG_CONTENT_FIELD).get(0));
            blog.setTitle(highlightPart.get(BLOG_TITLE_FIELD) == null ? null : highlightPart.get(BLOG_TITLE_FIELD).get(0));
            blogs.add(blog);
        });
        return blogs;

    }


    public boolean addBlogIndex(String id, String title, String content) throws IOException, SolrServerException {

        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", id);
        doc.setField(BLOG_TITLE_FIELD, title);
        doc.setField(BLOG_CONTENT_FIELD, content);


        UpdateResponse response = solrClient.add(COLLECTION, doc, 10000);
        return response != null && response.getStatus() == 0;


    }


    public boolean deleteBlogById(String id) throws IOException, SolrServerException {

        UpdateResponse response = solrClient.deleteById(COLLECTION, id, 10000);
        return response != null && response.getStatus() == 0;

    }

    public String getBlogById(String id) throws IOException, SolrServerException {
        //solrClient.getById(C)
        return solrClient.getById("ncublog",id).toString();

    }
}
