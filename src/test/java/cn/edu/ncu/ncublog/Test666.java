package cn.edu.ncu.ncublog;

import cn.edu.ncu.ncublog.model.User;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @Author lw
 * @Date 2018/8/30 20:08
 **/
public class Test666 {

    public static void main(String[] args) throws InterruptedException {
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        System.out.println(localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        String format = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(format);
        System.out.println(date);
        System.out.println(dateTime);
    }

    @Test
    public void test() {
        System.out.println(new Random().nextInt(94));
    }

    @Test
    public void test1() {
        User user = new User();
        user.setPassword("java");
        user.setHeadUrl("/javac");
        user.setSalt("amazing");
        user.setGender(1);
        String s = JSON.toJSONString(user,true);
        System.out.println(s);
    }

    @Test
    public void test2() {
        System.out.println(Math.ceil(3.4));
        System.out.println(Math.ceil(10.0/3));
        System.out.println(10 / 3);
    }
}
