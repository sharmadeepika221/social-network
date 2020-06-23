package com.social.network.message;

import com.social.network.message.domain.Message;
import com.social.network.message.repo.MessageRepository;
import com.social.network.message.repo.StateStoreRepository;
import com.social.network.message.snapshot.DomainUpdater;
import com.social.network.messageservice.common.EventPublisher;
import com.social.network.messageservice.common.JsonPojoSerde;
import com.social.network.messageservice.common.KafkaStreamsStarter;
import com.social.network.messageservice.model.Event;
import com.social.network.messageservice.util.MicroserviceUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Properties;

@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveCassandraRepositories
public class MessageApplication {

    private static final Logger logger = LoggerFactory.getLogger(MessageApplication.class);

    private static final String APP_ID = MicroserviceUtils.applicationId(MessageApplication.class);

    @Value("${kafka.bootstrapAddress}")
    private String kafkaBootstrapAddress;

    @Value("${apiVersion}")
    private int apiVersion;

    @Value("${kafkaTimeout:60000}")
    private long kafkaTimeout;

    @Value("${streamsStartupTimeout:20000}")
    private long streamsStartupTimeout;

    @Bean
    public KafkaStreams kafkaStreams() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        DomainUpdater snapshotBuilder = new DomainUpdater(new MessageRepository());
        Topology topology = streamsBuilder.build();
        snapshotBuilder.init(topology);
        KafkaStreamsStarter starter = new KafkaStreamsStarter(kafkaBootstrapAddress, topology, APP_ID);
        starter.setKafkaTimeout(kafkaTimeout);
        starter.setStreamsStartupTimeout(streamsStartupTimeout);
        return starter.start();
    }

    @Bean
    public EventPublisher eventPublisher() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonPojoSerde.class.getName());
        producerProps.put(ProducerConfig.CLIENT_ID_CONFIG, APP_ID);
        var kafkaProducer = new KafkaProducer<String, Event>(producerProps);
        return new EventPublisher(kafkaProducer, APP_ID, apiVersion);
    }



    @Bean
    public StateStoreRepository<Message> messageRepository() {
        return new StateStoreRepository<Message>(kafkaStreams(), DomainUpdater.MESSAGE_STORE);
    }

    public static void main(String[] args) {
        logger.info("Application ID: {}", APP_ID);
        SpringApplication.run(MessageApplication.class, args);
    }
}
