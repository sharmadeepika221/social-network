package com.social.network.message.controller;

import com.social.network.message.domain.Message;
import com.social.network.message.repo.MessageRepository;
import com.social.network.message.repo.StateStoreRepository;
import com.social.network.messageservice.common.EventPublisher;
import com.social.network.messageservice.model.MessageCreated;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    private MessageController controller;
    private StateStoreRepository<Message> stateStoreRepository;
    private MessageRepository messageRepository = new MessageRepository();
    private EventPublisher publisher;

    @Before
    public void setUp() {
        publisher = mock(EventPublisher.class);
        when(publisher.fire(any())).thenReturn(Mono.empty());

        stateStoreRepository = mock(StateStoreRepository.class);
        Message message = messageRepository.getDefault();
        message.create();
        when(stateStoreRepository.find("message1")).thenReturn(Optional.of(message));

        controller = new MessageController(publisher, stateStoreRepository,messageRepository);
    }

    @Test
    public void postMessage() {
        controller.getMessages(new Message("1", "111",  LocalDateTime.now(),"ABC", "a1", "m1", Message.State.CREATED))
                .block();

        var captor = ArgumentCaptor.forClass(MessageCreated.class);
        verify(publisher).fire(captor.capture());
       Assertions.assertThat(captor.getValue().getMessageId()).isEqualTo("ABC");
    }

}
