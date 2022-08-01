package com.tungstun.common.messaging;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
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
 *      &#64;Override
 *      public KafkaListenerContainerFactory kafkaListenerContainerFactory() {
 *          var factory = super.kafkaListenerContainerFactory()
 *          factory.setSomeNewSetting(xyz);
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

    @Autowired
    @Lazy
    private KafkaTemplate<String, Object> template;

    /**
     * Creates a new KafkaMessageProducer instance for the provided topic with the autowired default kafka template
     */
    public KafkaMessageProducer createMessageProducer(String topic) {
        return new KafkaMessageProducer(topic, template);
    }

    /**
     * Creates a simple KafkaTemplate for producer configs.
     * KafkaTemplate keys are Strings and values can Object to allow custom kafka event classes.
     */
    @Bean
    public KafkaTemplate<String, Object> defaultKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps()));
    }

    /**
     * KafkaListenerContainerFactory bean method with default settings for te MessageListenerContainer.
     * Method can be overridden to add or overwrite set classes or properties
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    /**
     * Creates DefaultKafkaConsumerFactory for consumer configurations
     * Key and value deserializers are wrapped in ErrorHandlingDeserializer to catch thrown exceptions
     */
    @Bean
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
