package com.lrtf.messaging.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerEntity {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerEntity.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "entidade", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(String message) {
        logger.info("Consumed message: {}", message);
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode idEntidade = jsonNode.get("idEntidade");

            if (idEntidade != null) {
                Thread.sleep(1000);
                logger.info("Process entity id: {}",  idEntidade.asText());
            } else {
                logger.error("Entity id not found in message");
            }

        } catch (IOException e) {
            logger.error("Failed process message {}", e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

