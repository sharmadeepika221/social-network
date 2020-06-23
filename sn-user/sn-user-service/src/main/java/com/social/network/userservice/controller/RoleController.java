package com.social.network.userservice.controller;

import com.social.network.userservice.dto.AssignRoleRequest;
import com.social.network.userservice.service.RoleService;
import com.social.network.userservice.validation.ValidationOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PutMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void assignRole(@Validated(ValidationOrder.class) @RequestBody final AssignRoleRequest request) {
        roleService.assign(request);
    }

}