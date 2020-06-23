package com.social.network.messageservice.common;

import com.social.network.messageservice.model.Event;
import com.social.network.messageservice.model.EventMetadata;
import com.social.network.messageservice.util.Topics;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class EventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(EventPublisher.class);

    private final KafkaProducer<String, Event> producer;
    private final String processId;
    private final int apiVersion;

    public EventPublisher(KafkaProducer<String, Event> producer, String processId, int apiVersion) {
        this.producer = producer;
        this.processId = processId;
        this.apiVersion = apiVersion;
    }

    public Mono<Void> fire(Event event) {
        return Mono.create(sink -> {
            fillOut(event);
            String topic = Topics.eventTopicName(event.getClass());
            ProducerRecord<String, Event> record = new ProducerRecord<>(topic, 0, event.getMetadata().getTimestamp(),
                    event.getAggId(), event);

            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    sink.success();
                    logger.debug("New {} event created: {}", event.getClass().getSimpleName(), event.getAggId());
                } else {
                    sink.error(exception);
                }
            });
        });
    }

    public void fillOut(Event event) {
        EventMetadata md = event.getMetadata();
        md.setEventId(generateId());
        md.setProcessId(processId);
        md.setVersion(apiVersion);

        if (md.getTimestamp() == 0) {
            md.setTimestamp(System.currentTimeMillis());
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
