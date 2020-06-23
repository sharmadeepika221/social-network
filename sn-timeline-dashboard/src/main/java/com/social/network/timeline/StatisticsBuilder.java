package com.social.network.timeline;

import com.social.network.messageservice.common.JsonPojoSerde;
import com.social.network.messageservice.model.MessageCreated;
import com.social.network.messageservice.util.Topics;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;

import static com.social.network.messageservice.common.StreamsUtils.materialized;
import static org.apache.kafka.common.serialization.Serdes.String;

public class StatisticsBuilder {

    private static final String MESSAGE_CREATED_TOPIC = Topics.eventTopicName(MessageCreated.class);
    private static final String TIMELINE_TOPIC = Topics.eventTopicName(Timeline.class);
    public static final String MESSAGE_STORE = "message_store";

    private final JsonPojoSerde<MessageCreated> messageCreatedJsonPojoSerde = new JsonPojoSerde<>(MessageCreated.class);
    private final JsonPojoSerde<Timeline> timelineJsonPojoSerde = new JsonPojoSerde<>(Timeline.class);

    private final StreamsBuilder builder;

    public StatisticsBuilder(StreamsBuilder builder) {
        this.builder = builder;
    }

    public void build() {
        buildTimeline();
    }

    private void buildTimeline() {
        KStream<String, MessageCreated> messageCreatedStream = builder
                .stream(MESSAGE_CREATED_TOPIC, Consumed.with(String(), messageCreatedJsonPojoSerde));

        KStream<String, Timeline> timelineStream = builder
                .stream(TIMELINE_TOPIC, Consumed.with(String(), timelineJsonPojoSerde));

        KTable<String, MessageCreated> timelineTable = messageCreatedStream
                .groupByKey(Serialized.with(String(), messageCreatedJsonPojoSerde))
                .reduce(new Reducer<MessageCreated>() {
                    @Override
                    public MessageCreated apply(MessageCreated value1, MessageCreated value2) {
                        return null;
                    }
                }, materialized(MESSAGE_CREATED_TOPIC, messageCreatedJsonPojoSerde));

        // publish changes to a view topic
        timelineTable.toStream().to(TIMELINE_TOPIC, Produced.with(String(), messageCreatedJsonPojoSerde));
    }

}
