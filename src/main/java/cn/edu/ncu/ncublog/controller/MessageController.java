package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.model.HostHolder;
import cn.edu.ncu.ncublog.model.Message;
import cn.edu.ncu.ncublog.model.User;
import cn.edu.ncu.ncublog.model.ViewObject;
import cn.edu.ncu.ncublog.service.MessageService;
import cn.edu.ncu.ncublog.service.UserService;
import cn.edu.ncu.ncublog.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lw
 * @Date 2018-09-02 20:39:05
 **/
@Controller
@RequestMapping("/msg")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);


    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping("/list")
    public String list(Model model) {

        try {


            User user = hostHolder.getUser();
            List<Message> conversationList = messageService.getConversationList(user.getId(), 0, 10);
            List<ViewObject> conversations = new ArrayList<>();
            for (Message message : conversationList) {
                ViewObject viewObject = new ViewObject();
                viewObject.set("message", message);
                int targetId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();
                viewObject.set("user", userService.getUserById(targetId));
                viewObject.set("unread", messageService.getConversationUnreadCount(user.getId(), message.getConversationId()));
                conversations.add(viewObject);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("列出信息失败...", e.getMessage());
        }

        return "letter";
    }

    @RequestMapping("/detail")
    public String detail(Model model, @RequestParam("conversationId") String conversationId) {
        try {

            System.out.println(conversationId);
            User user = hostHolder.getUser();
            List<Message> conversationDetail = messageService.getConversationDetail(conversationId, 0, 10);
            int frontId = 0;
            if (conversationDetail != null && conversationDetail.size() > 0) {
                Message message = conversationDetail.get(0);
                frontId = message.getToId() == user.getId() ? message.getFromId() : message.getToId();
            }
            User frontUser = userService.getUserById(frontId);
            List<ViewObject> messages = new ArrayList<>();
            conversationDetail.forEach((message) -> {
                ViewObject viewObject = new ViewObject();
                viewObject.set("message", message);
                messages.add(viewObject);
            });
            messageService.setRead(user.getId(), conversationId);
            model.addAttribute("messages", messages);
            model.addAttribute("targetUser", frontUser);
        } catch (Exception e) {
            logger.error("显示私信详情失败", e.getMessage());
            return "redirect:/";
        }
        return "letterDetail";
    }

    @RequestMapping("/addMessage")
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName, @RequestParam("content") String content) {
        try {

            if (toName.equals(hostHolder.getUser().getName())) {
                return CommonUtil.getJSONString(1, "请不要发信息给自己好吗");
            }
            Message message = new Message();
            User toUser = userService.getUserByName(toName);
            if (toUser == null) {
                return CommonUtil.getJSONString(1, "用户不存在");
            }
            message.setFromId(hostHolder.getUser().getId());
            message.setContent(HtmlUtils.htmlEscape(content));
            message.setToId(toUser.getId());
            message.setCreatedDate(LocalDateTime.now());
            int i = messageService.addMessage(message);
            if (i > 0) {
                return CommonUtil.getJSONString(0, "ok");
            } else {
                return CommonUtil.getJSONString(1, "添加失败");
            }

        } catch (Exception e) {
            logger.error("添加失败", e.getMessage());
            return CommonUtil.getJSONString(1, "添加失败");
        }
    }
}
