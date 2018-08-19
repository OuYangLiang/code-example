package com.personal.oyl.event;

import java.util.Date;

public class Event {
    private Long id;
    private String eventType;
    private Date eventTime;
    private String context;
    private String group;

    public Event() {
        super();
    }

    public Event(String eventType, Date eventTime, String context, String group) {
        super();
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.context = context;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
