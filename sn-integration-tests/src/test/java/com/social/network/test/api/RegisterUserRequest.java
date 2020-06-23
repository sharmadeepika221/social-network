package com.social.network.test.api;


public class RegisterUserRequest {
    private String email;
    private String password;
    private String name;
    private String membershipId;

    public RegisterUserRequest(String email, String password, String name, String membershipId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.membershipId = membershipId;
    }

    public RegisterUserRequest(String email, String toString, String toString1) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }
}
