package cn.edu.ncu.ncublog.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lw
 * @Date 2018/8/31 21:12
 **/
public class ViewObject {
    private Map<String, Object> map = new HashMap<>();

    public void set(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }
}
