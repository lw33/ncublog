package cn.edu.ncu.ncublog.model;

import java.time.LocalDateTime;

/**
 * @Author lw
 * @Date 2018-09-02 16:07:06
 **/
public class Comment {
    private int id;
    private String content;
    private int userId;
    private int EntityId;
    private int EntityType;
    private LocalDateTime createdDate;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return EntityId;
    }

    public void setEntityId(int entityId) {
        EntityId = entityId;
    }

    public int getEntityType() {
        return EntityType;
    }

    public void setEntityType(int entityType) {
        EntityType = entityType;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", EntityId=" + EntityId +
                ", EntityType=" + EntityType +
                ", createDate=" + createdDate +
                ", status=" + status +
                '}';
    }
}
