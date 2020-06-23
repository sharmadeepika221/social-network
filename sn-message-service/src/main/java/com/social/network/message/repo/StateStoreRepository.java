package com.social.network.message.repo;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.Optional;

public class StateStoreRepository<T> {

    private final KafkaStreams kafkaStreams;
    private final String storeName;

    public StateStoreRepository(KafkaStreams kafkaStreams, String storeName) {
        this.kafkaStreams = kafkaStreams;
        this.storeName = storeName;
    }

    public Optional<T> find(String id) {
        Objects.requireNonNull(id, "Null id");
        return Optional.ofNullable(store().get(id));
    }

    public Flux<T> findAll() {
        return Flux.create(sink -> {
            var iterator = store().all();

            while (iterator.hasNext()) {
                sink.next(iterator.next().value);
            }
            iterator.close();
            sink.complete();
        });
    }

    private ReadOnlyKeyValueStore<String, T> store() {
        return kafkaStreams.store(storeName, QueryableStoreTypes.<String, T>keyValueStore());
    }
}
