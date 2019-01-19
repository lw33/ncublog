package cn.edu.ncu.ncublog.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author lw
 * @Date 2018-09-01 22:09:13
 **/

@Controller
public class TestController {

    @Autowired
    private SolrClient solrClient;

    @RequestMapping("/ts")
    @ResponseBody
    public String ts(String id) throws IOException, SolrServerException {

        System.out.println(solrClient);

        return solrClient.getById("ncublog", id).toString();
    }
    @RequestMapping("/fff")
    public String fff(Model model) {
        System.out.println("TestController.fff");
        //return new User();
        //return CommonUtil.getJSONString(0, "java");

        //model.addAttribute("id", 1000);
        model.addAttribute("time", LocalDateTime.now());
        //model.addAttribute("time", new Date());

        return "test";
    }
}
