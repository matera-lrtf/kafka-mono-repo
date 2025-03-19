package com.lrtf.messaging.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lrtf.messaging.KafkaMessageProducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class KafkaMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @Transactional
    @KafkaListener(topics = "processo", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(String message) {
        logger.info("Consumed message: {}", message);
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            JsonNode nomePrcessoNode = jsonNode.get("nomeProcesso");

            if(nomePrcessoNode != null && "BLA".equals(nomePrcessoNode.asText())) {
                logger.info("Process name is BLA, starting generation messages");

                List<String> batchMessages = new ArrayList<>();
                int batchSize = 5;

                for (int i = 1; i <= 12; i++) {
                    String outMessage = String.format("{\"nomeProcesso\": \"BLA\", \"idEntidade\": %d }", i);
                    batchMessages.add(outMessage);

                    if(i == 6) {
                        throw  new RuntimeException();
                    }

                    if (batchMessages.size() >= batchSize) {
                        kafkaMessageProducer.sendBatchMessage("entidade", batchMessages);
                        batchMessages.clear();
                    }


                }

                if (!batchMessages.isEmpty()) {
                    kafkaMessageProducer.sendBatchMessage("entidade", batchMessages);
                }


            } else {
                logger.info("Process name is not BLA");
            }

        } catch (IOException e) {
            logger.error("Failed process message {}", e.getMessage());
        }
    }

}
