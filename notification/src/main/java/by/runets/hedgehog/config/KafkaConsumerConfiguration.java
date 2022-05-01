package by.runets.hedgehog.config;

import by.runets.hedgehog.event.VerificationEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;

import static by.runets.hedgehog.utils.Constants.KAFKA_ADDRESS_KEY;
import static by.runets.hedgehog.utils.Constants.KAFKA_GROUP_KEY;

@Configuration
public class KafkaConsumerConfiguration {

    @Value(KAFKA_ADDRESS_KEY)
    private String kafkaAddress;
    @Value(KAFKA_GROUP_KEY)
    private String kafkaGroup;

    @Bean
    public ConsumerFactory<String, VerificationEvent> consumerFactory() {
        var timestampEventDeserializer = new JsonDeserializer<>(VerificationEvent.class);
        timestampEventDeserializer.setRemoveTypeHeaders(false);
        timestampEventDeserializer.addTrustedPackages("*");
        timestampEventDeserializer.setUseTypeMapperForKey(true);

        var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroup);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), timestampEventDeserializer);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, VerificationEvent>> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, VerificationEvent>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
