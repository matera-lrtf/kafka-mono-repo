package com.lrtf.messaging.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerEntity {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerEntity.class);
    private final ObjectMapper objectMapper;

    public KafkaConsumerEntity() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(
        topics = "teste_processo",
        groupId = "${spring.kafka.consumer.group-id}",
        autoStartup = "true"
    )
    public void consumer(String message) {
        logger.info("Mensagem recebida: {}", message);
        try {
            JsonNode rootNode = objectMapper.readTree(message);
            
            // Extrair o payload
            String payloadString = rootNode.path("payload").asText();
            if (payloadString != null && !payloadString.isEmpty()) {
                JsonNode payloadNode = objectMapper.readTree(payloadString);
                logger.info("Payload processado: {}", payloadNode);
                
                // Processar os dados
                processarPayload(payloadNode);
            }
        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: {}", e.getMessage(), e);
        }
    }

    //Metodo que processa o payload que vem na mensagem
    private void processarPayload(JsonNode payloadNode) {
        String id = payloadNode.path("id").asText(null);
        String nome = payloadNode.path("nome").asText(null);
        String nomeProcesso = payloadNode.path("nomeProcesso").asText(null);
        
        if (id != null && !id.isEmpty()) {
            logger.info("Processando entidade - ID: {}, Nome: {}, Processo: {}", 
                id, nome, nomeProcesso);
            // TODO: implementar a lógica de processamento da entidade (nem sei o que pode ser feito kkkkk)
        } else {
            logger.info("Mensagem de controle: {}", payloadNode);
        }
    }
}

