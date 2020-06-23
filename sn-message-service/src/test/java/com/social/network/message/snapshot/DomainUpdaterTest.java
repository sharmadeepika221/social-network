package com.social.network.message.snapshot;

import com.social.network.message.common.test.StreamsTester;
import com.social.network.message.domain.Message;
import com.social.network.message.repo.MessageRepository;
import com.social.network.messageservice.model.MessageCreated;
import com.social.network.messageservice.model.MessageDeleted;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DomainUpdaterTest {

    private StreamsTester tester;

    @Before
    public void setUp() throws Exception {
        tester = new StreamsTester(getClass().getName());

        Topology topology = new Topology();
        new DomainUpdater(new MessageRepository()).init(topology);

        tester.setUp(topology);
    }

    @Test
    public void test() throws Exception {
        tester.sendEvents(getClass().getResource("message-created.json"), MessageCreated.class);
        tester.sendEvents(getClass().getResource("message-deleted.json"), MessageDeleted.class);

        ReadOnlyKeyValueStore<String, Message> messageStore = tester.getStore(DomainUpdater.MESSAGE_STORE);

        assertThat(tester.count(messageStore)).isEqualTo(4);
        Message message1 = messageStore.get("1");
        Message message2 = messageStore.get("2");

        assertThat(message1.getState()).isEqualTo(Message.State.CREATED);
        assertThat(message2.getState()).isEqualTo(Message.State.CREATED);

    }

    @After
    public void tearDown() throws Exception {
        tester.close();
    }
}
