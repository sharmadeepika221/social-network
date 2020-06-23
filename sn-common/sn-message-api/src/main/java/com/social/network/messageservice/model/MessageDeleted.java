package com.social.network.messageservice.model;

public class MessageDeleted extends Event {

    private String messageId;

    private MessageDeleted() {
    }

    public MessageDeleted(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String getAggId() {
        return messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "MessageDeleted{" +
                "messageId='" + messageId + '\'' +
                '}';
    }
}
