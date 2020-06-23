package com.social.network.message.controller;

import com.social.network.message.domain.Message;
import com.social.network.message.repo.MessageRepository;
import com.social.network.message.repo.StateStoreRepository;
import com.social.network.messageservice.common.EventPublisher;
import com.social.network.messageservice.model.Event;
import com.social.network.messageservice.model.MessageCreated;
import com.social.network.messageservice.model.MessageDeleted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.social.network.message.domain.Message.State;

@RestController
@RequestMapping(path = "/command", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final EventPublisher publisher;
    private final StateStoreRepository<Message> stateStoreRepository;
    private final MessageRepository messageRepository;

    public MessageController(EventPublisher publisher,
                             StateStoreRepository<Message> stateStoreRepository,
                             MessageRepository messageRepository) {
        this.publisher = publisher;
        this.stateStoreRepository = stateStoreRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> getMessages(@RequestBody Message request) {
        Event event = new MessageCreated(request.getMessageId(), request.getTimelineId(),
                request.getDate(), request.getMessage());
        logger.debug("Createing a request: {}", event);
        messageRepository.save(request);
        return publisher.fire(event);
    }

    @PatchMapping("/messages/{messageId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteMessage(@PathVariable String messageId, @RequestBody Message request) {
        return Mono.<Event>create(sink -> {
            Message.State newState = Message.State.valueOf(request.getState().toString());
            Message message = stateStoreRepository.find(messageId).orElseThrow(
                    () -> new NotFoundException("Message not found", messageId));
            Event event;

             if (newState == State.DELETED) {
                event = new MessageDeleted(messageId);
                logger.debug("Deleting the message: {}", event);
            } else {
                throw new UnsupportedOperationException("State " + newState + " not implemented yet");
            }
             messageRepository.delete(message);
            event.timestamp(request.getDate());
            sink.success(event);
        }).flatMap(publisher::fire);
    }

}
