package com.social.network.message.repo;

import com.social.network.message.domain.Message;
import org.springframework.util.ReflectionUtils;

public class MessageRepository {

    private final Message defaultMessage = create("1", "Msg 1");

    public Message getDefault() {
        return defaultMessage;
    }

    // create instance using reflection in order not to create public constructor
    private static Message create(String id, String name) {
        try {
            return ReflectionUtils.accessibleConstructor(Message.class, String.class, String.class)
                    .newInstance(id, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Message message) {
    }

    public void delete(Message message) {
    }
}
