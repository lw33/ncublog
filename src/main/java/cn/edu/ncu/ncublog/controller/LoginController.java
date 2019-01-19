package cn.edu.ncu.ncublog.controller;

import cn.edu.ncu.ncublog.model.HostHolder;
import cn.edu.ncu.ncublog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author lw
 * @Date 2018-09-01 17:55:00
 **/
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = {"/reg/"})
    public String register(Model model, String username, String password,
                           @RequestParam(value = "next", required = false) String next,
                           HttpServletResponse response,
                           @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme) {

        next = next == null ? "/" : next;
        if (next.startsWith("http")) {
            next = "/";
        }
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ncuPass", map.get("ticket"));
                if (rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:"+next;
            }
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            return "redirect:" + next;
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            model.addAttribute("msg", "服务器错误");
            return "login";
        }
    }

    @RequestMapping(path = {"/login/"})
    public String login(Model model, String username, String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {

        next = next == null ? "/" : next;
        if (next.startsWith("http")) {
            next = "/";
        }
        try {
            if (hostHolder.getUser() != null) {
                return "redirect:" + next;
            }
            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ncuPass", map.get("ticket"));
                cookie.setPath("/");
                if (rememberme) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return "redirect:" + next;
            }
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            return "redirect:" + next;
        } catch (Exception e) {
            logger.error("登入异常" + e.getMessage());
            model.addAttribute("msg", "服务器错误");
            return "login";
        }
    }

    @RequestMapping("/reglogin")
    public String regLoging(Model model,@RequestParam(value = "next", required = false) String next) {
        next = next == null ? "/" : next;
        if (next.startsWith("http")) {
            next = "/";
        }
        model.addAttribute("next", next);
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(@CookieValue("ncuPass") String ncuPass) {
        try {

            userService.logout(ncuPass);
        } catch (Exception e) {
            logger.error("登出失败...", e.getMessage());
        }
        return "redirect:/";
    }
}
