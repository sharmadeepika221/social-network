package com.social.network.authservice.events;

import com.social.network.authservice.entity.User;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class UserDeserializer extends JsonDeserializer<User> {

    public UserDeserializer() {
        super(User.class);
    }
}
