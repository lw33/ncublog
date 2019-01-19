package cn.edu.ncu.ncublog.model;

import java.time.LocalDateTime;

/**
 * @Author lw
 * @Date 2018-09-02 20:08:42
 **/
public class Message {


    /*  `id` INT NOT NULL AUTO_INCREMENT,
      `from_id` INT NULL,
      `to_id` INT NULL,
      `content` TEXT NULL,
      `created_date` DATETIME NULL,
      `hasRead` INT NULL,
      `conversation_id` VARCHAR(45) NOT NULL,*/
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private LocalDateTime createdDate;
    private int hasRead;
    private String conversationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }
    

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead= hasRead;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                ", hasRead=" + hasRead +
                ", conversationId='" + conversationId + '\'' +
                '}';
    }

    public String getConversationId() {
        if (fromId > toId) {
            return toId + "_" + fromId;
        } else {
            return fromId + "_" + toId;
        } 
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
