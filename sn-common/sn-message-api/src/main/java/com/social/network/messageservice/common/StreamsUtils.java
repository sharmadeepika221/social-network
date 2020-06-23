package com.social.network.messageservice.common;

import com.social.network.messageservice.model.Event;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.social.network.messageservice.util.Topics.eventTopicName;

public class StreamsUtils {

    private static final Logger logger = LoggerFactory.getLogger(StreamsUtils.class);

    private StreamsUtils() {
    }

    public static <V> Materialized<String, V, KeyValueStore<Bytes, byte[]>> materialized(String storeName,
            Serde<V> serde) {
        return Materialized.<String, V, KeyValueStore<Bytes, byte[]>>as(storeName)
            .withKeySerde(Serdes.String()).withValueSerde(serde);
    }

    public static String storeName(Class stored) {
        return stored.getSimpleName().replaceAll("(.)(\\p{Upper}+)", "$1_$2").toLowerCase();
    }

    public static <E extends Event, D> void addProcessor(Topology topology, Class<E> eventType,
                                                         EventProcessor<E, D> proc, String store) {
        String topic = eventTopicName(eventType);
        topology.addSource(eventType.getSimpleName() + "Source", Serdes.String().deserializer(),
                new JsonPojoSerde<E>(eventType), topic)
                .addProcessor(eventType.getSimpleName() + "Process",
                    () -> new ProcessorWrapper<E, D>(proc, topic, store),
                eventType.getSimpleName() + "Source");
    }

    public static <D, E extends Event> void addStore(Topology topology, Class<D> domainType, String store,
            Class<E>... eventTypes) {
        StoreBuilder<KeyValueStore<String, D>> msgStoreBuilder = Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore(store), Serdes.String(), new JsonPojoSerde<D>(domainType))
                .withLoggingDisabled();

        String[] processorNames = Stream.of(eventTypes)
            .map(event -> event.getSimpleName() + "Process")
            .collect(Collectors.toList()).toArray(new String[eventTypes.length]);

        topology.addStateStore(msgStoreBuilder, processorNames);
    }

    @FunctionalInterface
    public interface EventProcessor<E extends Event, D> {

        void process(String eventId, E event, KeyValueStore<String, D> store);
    }

    private static class ProcessorWrapper<E extends Event, D> extends AbstractProcessor<String, E> {

        private final String storeName;
        private final String topic;
        private final EventProcessor<E, D> processor;

        private KeyValueStore<String, D> store;

        private ProcessorWrapper(EventProcessor<E, D> processor, String topic, String storeName) {
            this.processor = processor;
            this.topic = topic;
            this.storeName = storeName;
        }

        @Override
        public void init(ProcessorContext context) {
            store = (KeyValueStore<String, D>)context.getStateStore(storeName);
        }

        @Override
        public void process(String eventId, E event) {
            logger.debug("Event received from topic {}: {}->{}", topic, eventId, event);
            processor.process(eventId, event, store);
        }

        @Override
        public void close() {
            store.close();
        }
    }
}
