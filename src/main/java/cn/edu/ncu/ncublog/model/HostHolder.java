package cn.edu.ncu.ncublog.model;

import org.springframework.stereotype.Component;

/**
 * @Author lw
 * @Date 2018-09-01 19:38:17
 **/
@Component
public class HostHolder {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public User getUser() {
        return userThreadLocal.get();
    }

    public void setUser(User user) {
        userThreadLocal.set(user);
    }

    public void clear() {
        userThreadLocal.remove();
    }
}
