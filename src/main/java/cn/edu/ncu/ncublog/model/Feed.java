package cn.edu.ncu.ncublog.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.time.LocalDateTime;

/**
 * @Author lw
 * @Date 2018-09-04 12:40:02
 **/
public class Feed {

    private int id;
    private int type;
    private int userId;
    private LocalDateTime createdDate;


    // json
    private String data;

    private JSONObject jsonData;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        jsonData = JSON.parseObject(data);
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", type=" + type +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", data='" + data + '\'' +
                ", jsonData=" + jsonData +
                '}';
    }

    public String get(String key) {
        return jsonData == null ? null : jsonData.getString(key);
    }
}
