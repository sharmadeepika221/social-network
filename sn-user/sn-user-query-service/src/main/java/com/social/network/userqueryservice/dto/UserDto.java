package com.social.network.userqueryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    private String id;
    private String name;
    private String lastVisit;
    private String membership_id;

    public UserDto(String id, String name, String lastVisit, String membership_id) {
        this.id = id;
        this.name = name;
        this.lastVisit = lastVisit;
        this.membership_id = membership_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastVisit='" + lastVisit + '\'' +
                ", membership_id='" + membership_id + '\'' +
                '}';
    }
}
