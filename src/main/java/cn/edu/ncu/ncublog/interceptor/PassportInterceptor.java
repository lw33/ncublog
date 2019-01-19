package cn.edu.ncu.ncublog.interceptor;

import cn.edu.ncu.ncublog.dao.LoginTicketDao;
import cn.edu.ncu.ncublog.dao.UserDao;
import cn.edu.ncu.ncublog.model.HostHolder;
import cn.edu.ncu.ncublog.model.LoginTicket;
import cn.edu.ncu.ncublog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @Author lw
 * @Date 2018-09-01 19:30:55
 **/
@Component
public class PassportInterceptor implements HandlerInterceptor {


    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ncuPass = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("ncuPass".equals(cookie.getName())) {
                    ncuPass = cookie.getValue();
                    break;
                }
            }
        }

        if (ncuPass != null) {
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ncuPass);
            if ((loginTicket == null || loginTicket.getExpired().isBefore(LocalDateTime.now()) || loginTicket.getStatus() == 1)) {
                return true;
            }

            User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
