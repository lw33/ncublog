package cn.edu.ncu.ncublog.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lw
 * @Date 2018-09-03 16:34:38
 **/
public class EventModel {

    /**
     * 事件类型
     */
    private EventType eventType;

    /**
     * 发出者id
     */
    private int actorId;

    /**
     * 事件对应实体类型
     */
    private int entityType;

    /**
     * 事件对应实体 id
     */
    private int entityId;

    /**
     * 事件接收者
     */
    private int entityOwnerId;

    /**
     * 扩展字段
     */
    private Map<String, String> exts = new HashMap<>();

    public EventModel() {
    }

    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventModel set(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public String get(String key) {
        return exts.get(key);
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "eventType=" + eventType +
                ", actorId=" + actorId +
                ", entityType=" + entityType +
                ", entityId=" + entityId +
                ", entityOwnerId=" + entityOwnerId +
                ", exts=" + exts +
                '}';
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
