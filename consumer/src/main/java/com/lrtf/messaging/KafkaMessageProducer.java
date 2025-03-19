package com.lrtf.messaging;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public void sendBatchMessage(String topic, List<String> messages) {
        if(!messages.isEmpty()) {
            messages.forEach(message -> kafkaTemplate.send(topic, message));
        }
    }

}
