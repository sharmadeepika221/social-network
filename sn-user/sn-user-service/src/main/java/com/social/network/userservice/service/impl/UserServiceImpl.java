package com.social.network.userservice.service.impl;

import com.social.network.userservice.dto.CreateUserRequest;
import com.social.network.userservice.entity.Role;
import com.social.network.userservice.entity.User;
import com.social.network.userservice.repository.UserRepository;
import com.social.network.userservice.service.UserService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private KafkaTemplate<String, User> kafkaTemplate;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public String create(final CreateUserRequest request) {
        final User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        fireMembershipCreatedEvent(user);
        return userRepository.save(user).getId();
    }

    private void fireMembershipCreatedEvent(User user) {
        kafkaTemplate.send("membership", user.getId() + "created", user);
    }
}
