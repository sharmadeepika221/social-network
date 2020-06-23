package com.social.network.userservice.repository;

import java.util.Optional;

import com.social.network.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

}
