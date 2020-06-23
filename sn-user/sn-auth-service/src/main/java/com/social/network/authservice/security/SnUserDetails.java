package com.social.network.authservice.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import com.social.network.authservice.entity.User;

@Getter
public class SnUserDetails extends org.springframework.security.core.userdetails.User {

    private final String userId;

    public SnUserDetails(final User user) {
        super(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

        this.userId = user.getName();
    }

}
