package com.social.network.messageservice.model;

import java.time.LocalDateTime;

public class MessageCreated extends Event {

    private String messageId;
    private String timelineId; // unused
    private LocalDateTime date;
    private String message;

    private MessageCreated() {
    }

    public MessageCreated(String messageId, String timelineId, LocalDateTime date, String message) {
        this.messageId = messageId;
        this.timelineId = timelineId;
        this.date = date;
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(String timelineId) {
        this.timelineId = timelineId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getAggId() {
        return messageId;
    }

    @Override
    public String toString() {
        return "MessageCreated{" +
                "messageId='" + messageId + '\'' +
                ", timelineId='" + timelineId + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
