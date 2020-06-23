package com.social.network.message.snapshot;

import com.social.network.message.domain.Message;
import com.social.network.message.repo.MessageRepository;
import com.social.network.messageservice.model.MessageCreated;
import com.social.network.messageservice.model.MessageDeleted;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.social.network.messageservice.common.StreamsUtils.addProcessor;
import static com.social.network.messageservice.common.StreamsUtils.addStore;

public class DomainUpdater {

    private static final Logger logger = LoggerFactory.getLogger(DomainUpdater.class);

    public static final String MESSAGE_STORE = "message_store";

    private final MessageRepository messageRepository;

    public DomainUpdater(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void init(Topology topology) {
        addProcessor(topology, MessageCreated.class, (eventId, event, store) -> {
            Message message = findMessage(store, event.getMessageId());
            message.create();
            store.put(message.getMessageId(), message);
        }, MESSAGE_STORE);

        addStore(topology, Message.class, MESSAGE_STORE, new Class[] {
                MessageCreated.class, MessageDeleted.class});
    }

    private Message findMessage(KeyValueStore<String, Object> store, String messageId) {
        return (Message) Objects.requireNonNull(store.get(messageId), "Message not found: " + messageId);
    }
}
