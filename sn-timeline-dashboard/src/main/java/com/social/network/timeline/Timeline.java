package com.social.network.timeline;

import com.social.network.messageservice.model.Event;

import java.time.LocalDateTime;

public class Timeline extends Event {

    private String timelineId;
    private String customerId; // unused
    private LocalDateTime date;

    @Override
    public String getAggId() {
        return null;
    }

    public String getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(String timelineId) {
        this.timelineId = timelineId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
