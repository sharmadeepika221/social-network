package com.social.network.authservice.events;

import com.social.network.authservice.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public class UserKafkaListener {
    private final Logger log = LoggerFactory.getLogger(UserKafkaListener.class);

    public UserKafkaListener() {
        super();
    }

    @KafkaListener(topics = "membership")
    public void membershipAction(User user, Acknowledgment acknowledgment) {
        log.info("Membership Created " + user.getName());
        acknowledgment.acknowledge();
    }
}
