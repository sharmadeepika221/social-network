package com.social.network.userservice.service;


import com.social.network.userservice.dto.CreateUserRequest;

public interface UserService {
    String create(CreateUserRequest request);
}
