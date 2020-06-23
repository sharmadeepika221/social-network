package com.social.network.message.domain;

import java.time.LocalDateTime;

public class Message {

    public enum State {
        CREATED, DELETED;
    }

    private String messageId;
    private String timelineId; // unused
    private LocalDateTime date;
    private String message;
    private String customerId;
    private String membershipId;
    private State state;

    private Message() {
    }

    public Message(String messageId, String timelineId, LocalDateTime date, String message, String customerId, String membershipId, State state) {
        this.messageId = messageId;
        this.timelineId = timelineId;
        this.date = date;
        this.message = message;
        this.customerId = customerId;
        this.membershipId = membershipId;
        this.state = state;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", timelineId='" + timelineId + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", customerId='" + customerId + '\'' +
                ", membershipId='" + membershipId + '\'' +
                '}';
    }

    public void create() {
        if (state != State.DELETED) {
            throw new IllegalStateException("Cannot create " + state + " message");
        }
        state = State.CREATED;
    }

    public void delete() {
        if (state != State.CREATED) {
            throw new IllegalStateException("Cannot create " + state + " message");
        }
        state = State.DELETED;
    }
}
