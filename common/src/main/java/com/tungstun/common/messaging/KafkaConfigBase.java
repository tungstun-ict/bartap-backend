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
 * <p>Base class for Consumer and Producer configuration classes.
 * Class contains general kafka configuration properties with type mappings for a set of custom message classes</p>
 *
 * <p>ConsumerConfig class example:</p>
 * <pre>
 *  public class ConsumerConfig extends KafkaConfigBase {
 *      private static final Set< Class<?> > CLASSES = Set.of(
 *          SomeConsumableMessage.class
 *      );
 *
 *      &#64;Bean
 *      public KafkaListenerContainerFactory kafkaListenerContainerFactory() {
 *          ...
 *          factory.setConsumerFactory(defaultConsumerFactory(CLASSES));
 *          ...
 *      }
 *  }
 * </pre>
 *
 * <p>ProducerConfig class example:</p>
 * <pre>
 *  public class ProducerConfig extends KafkaConfigBase {
 *      private static final String TOPIC = "topic";
 *      private static final Set< Class<?> > CLASSES = Set.of(
 *          SomeProducibleMessage.class
 *      );
 *
 *      &#64;Bean
 *      public KafkaMessageProducer kafkaMessageProducer() {
 *          return new KafkaMessageProducer(TOPIC, defaultKafkaTemplate(CLASSES));
 *      }
 *  }
 * </pre>
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
     * KafkaTemplate keys are Strings and values can Object to allow custom kafka message classes.
     */
    private KafkaTemplate<String, Object> defaultKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps()));
    }

    /**
     * Creates DefaultKafkaConsumerFactory for consumer configs.
     * Key and value deserializers are wrapped in ErrorHandlingDeserializer to catch thrown exceptions
     */
    public ConsumerFactory<String, Object> defaultConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                configProps(),
                new ErrorHandlingDeserializer<>(new StringDeserializer()),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>()));
    }

    /**
     * Creates general configuration properties for consumers and producers.
     * Configuration properties contain created type mappings of given classes
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
