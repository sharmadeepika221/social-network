package com.social.network.userservice.service.impl;

import com.social.network.userservice.dto.AssignRoleRequest;
import com.social.network.userservice.repository.UserRepository;
import com.social.network.userservice.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public void assign(final AssignRoleRequest request) {
    }
}
