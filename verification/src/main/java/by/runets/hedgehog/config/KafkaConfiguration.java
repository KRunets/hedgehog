package by.runets.hedgehog.config;

import by.runets.hedgehog.event.VerificationEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

import static by.runets.hedgehog.utils.Constants.KAFKA_ADDRESS_KEY;
import static by.runets.hedgehog.utils.Constants.KAFKA_GROUP_KEY;

@Configuration
public class KafkaConfiguration {

    @Value(KAFKA_ADDRESS_KEY)
    private String kafkaAddress;
    @Value(KAFKA_GROUP_KEY)
    private String kafkaGroup;

    @Bean
    public ProducerFactory<String, VerificationEvent> producerFactory() {
        var props = new HashMap<String, Object>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroup);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, VerificationEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
