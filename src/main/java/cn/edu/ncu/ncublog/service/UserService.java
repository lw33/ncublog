package cn.edu.ncu.ncublog.service;

import cn.edu.ncu.ncublog.dao.LoginTicketDao;
import cn.edu.ncu.ncublog.dao.UserDao;
import cn.edu.ncu.ncublog.model.LoginTicket;
import cn.edu.ncu.ncublog.model.User;
import cn.edu.ncu.ncublog.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @Author lw
 * @Date 2018-09-01 11:29:25
 **/
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    public User getUserById(int id) {
        return userDao.selectById(id);
    }

    public int changeUserInfo(User user) {

        return userDao.updateUser(user);

    }

    public Map<String, String> register(String name, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDao.selectByName(name);
        if (user != null) {
            map.put("msg", "用户名已被注册");
            return map;
        }
        user = new User();
        user.setName(name);
        String salt = UUID.randomUUID().toString().substring(0, 5);
        user.setSalt(salt);
        user.setHeadUrl("/images/headers/" + new Random().nextInt(97) + ".jpg");
        user.setBirth(LocalDate.now());
        user.setRegisterDate(LocalDateTime.now());
        user.setPassword(CommonUtil.MD5(password + salt));
        userDao.addUser(user);

        String ticket = addLoginTicket(userDao.selectByName(name).getId());
        map.put("ticket", ticket);
        return map;
    }

    public Map<String, String> login(String name, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDao.selectByName(name);
        if (CommonUtil.MD5(user.getSalt() + password).equals(user.getPassword())) {
            map.put("msg", "用户名或密码错误");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setExpired(LocalDateTime.now().plusDays(30));
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1);
    }

    public User getUserByName(String name) {
        return userDao.selectByName(name);
    }
}
