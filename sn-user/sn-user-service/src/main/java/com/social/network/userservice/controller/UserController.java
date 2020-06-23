package com.social.network.userservice.controller;

import java.security.Principal;
import javax.validation.Valid;

import com.social.network.userservice.dto.CreateUserRequest;
import com.social.network.userservice.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @PostMapping
    public void createUser(@Valid @RequestBody final CreateUserRequest createUserRequest) {
        userService.create(createUserRequest);
    }
}