package com.tungstun.security.messaging.out;

import com.tungstun.security.messaging.out.message.UserCreated;
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

import static com.tungstun.sharedlibrary.messaging.MessagingUtils.createTypeMapping;

@Configuration("securityProducerConfig")
@EnableKafka
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean("securityTemplate")
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
        props.put(JsonDeserializer.TYPE_MAPPINGS, String.join(",",
                createTypeMapping(UserCreated.class)
        ));
        return props;
    }

    public DelegatingByTypeSerializer serializer() {
        return new DelegatingByTypeSerializer(Map.of(
                Integer.class, new IntegerSerializer(),
                Long.class, new LongSerializer(),
                byte[].class, new ByteArraySerializer(),
                Bytes.class, new BytesSerializer(),
                String.class, new StringSerializer(),
                UserCreated.class, new JsonSerializer<UserCreated>()
        ));
    }
}
