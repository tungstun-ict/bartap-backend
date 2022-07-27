package com.tungstun.common.messaging;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Base class for Kafka Configuration classes.
 * Class contains general kafka configuration properties with type mappings for a set event classes</p>
 *
 * <p>KafkaConfig class example:</p>
 * <pre>
 *  public class KafkaConfig extends KafkaConfigBase {
 *      private static final String TOPIC = "topic";
 *
 *      &#64;Bean
 *      public NewTopic topic() {
 *         return new NewTopic(TOPIC, 1, (short) 1);
 *      }
 *
 *      &#64;Bean
 *      public KafkaMessageProducer kafkaMessageProducer() {
 *          return createMessageProducer(TOPIC);
 *      }
 *
 *      &#64;Bean
 *      public KafkaListenerContainerFactory kafkaListenerContainerFactory() {
 *          KafkaListenerContainerFactory factory = ...
 *          factory.setSomeSetting(xyz);
 *          ...
 *          return factory;
 *      }
 *  }
 */
public abstract class KafkaConfigBase {
    /**
     * Kafka Server URLs
     */
    @Value("${spring.kafka.bootstrap-servers:localhost:9292}")
    protected String bootstrapServers;


    /**
     * Creates a new KafkaMessageProducer instance for the provided topic with the default kafka template
     */
    public KafkaMessageProducer createMessageProducer(String topic) {
        return new KafkaMessageProducer(topic, defaultKafkaTemplate());
    }

    /**
     * Creates a simple KafkaTemplate for producer configs.
     * KafkaTemplate keys are Strings and values can Object to allow custom kafka event classes.
     */
    private KafkaTemplate<String, Object> defaultKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps()));
    }

    /**
     * Creates DefaultKafkaConsumerFactory for consumer configurations
     * Key and value deserializers are wrapped in ErrorHandlingDeserializer to catch thrown exceptions
     */
    public ConsumerFactory<String, Object> defaultConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                configProps(),
                new ErrorHandlingDeserializer<>(new StringDeserializer()),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>()));
    }

    /**
     * Creates general kafka configuration properties.
     * Configuration properties contain created type mappings of registered event DTO classes
     *
     * @return Map of configuration properties
     */
    private Map<String, Object> configProps() {
        return new HashMap<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "com.tungstun.**",
                JsonDeserializer.TYPE_MAPPINGS, convertToMapping(EventClassRegister.getEventClasses())
        ));
    }

    /**
     * Converts a collection of Class<?> objects to json (de)serializer type mappings.
     * <p>Example of type mapping: </p>
     * <pre>"SomeMessageClass:com.tungstun.common.SomeMessageClass"</pre>
     *
     * @return String of type mappings joined with ',' as delimiter
     */
    private String convertToMapping(Collection<Class<?>> customClasses) {
        return customClasses.stream()
                .map(clazz -> String.format("%s:%s", clazz.getSimpleName(), clazz.getCanonicalName()))
                .collect(Collectors.joining(","));
    }
}
