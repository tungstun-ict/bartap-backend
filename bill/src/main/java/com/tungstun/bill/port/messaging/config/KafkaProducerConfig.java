package com.tungstun.bill.port.messaging.config;

import com.tungstun.common.messaging.KafkaMessageProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.DelegatingByTypeSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    private static final String TOPIC = "bill";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public NewTopic core() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public KafkaMessageProducer kafkaMessageProducer() {
        return new KafkaMessageProducer(TOPIC, kafkaTemplate());
    }

    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), serializer());
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS,String.join(",",
                ""
        ));
        return props;
    }

    public DelegatingByTypeSerializer serializer() {
        return new DelegatingByTypeSerializer(Map.of(
                Integer.class, new IntegerSerializer(),
                Long.class, new LongSerializer(),
                byte[].class, new ByteArraySerializer(),
                Bytes.class, new BytesSerializer(),
                String.class, new StringSerializer()
        ));
    }
}
