package by.runets.hedgehog.producer.impl;

import by.runets.hedgehog.event.VerificationEvent;
import by.runets.hedgehog.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventProducerImpl implements EventProducer<VerificationEvent> {

    @Value("kafka.topic")
    private String kafkaTopic;
    @Autowired
    private KafkaTemplate<String, VerificationEvent> kafkaTemplate;

    @Override
    public void fireEvent(VerificationEvent event) {
        kafkaTemplate.send(kafkaTopic, event);
    }

    public void setKafkaTemplate(KafkaTemplate<String, VerificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void setKafkaTopic(String topic) {
        this.kafkaTopic = topic;
    }
}
