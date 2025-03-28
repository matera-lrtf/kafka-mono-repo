package com.lrtf.messaging.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lrtf.messaging.service.ProcessoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class KafkaMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProcessoService processoService;

    public KafkaMessageConsumer(ProcessoService processoService) {
        this.processoService = processoService;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(topics = "processo", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional(transactionManager = "transactionManager")
    public void consumer(String message) {
        logger.info("Mensagem recebida: {}", message);
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            String nomeProcesso = jsonNode.path("nomeProcesso").asText();

            if ("BLA".equals(nomeProcesso)) {
                logger.info("Processo BLA identificado, gerando entidades...");

                // Gerar 12 entidades
                for (int i = 1; i <= 12; i++) {
                    try {
                        processoService.processarEntidade(nomeProcesso, i);
                        
                        // Simular erro na sexta entidade
//                        if (i == 6) {
//                            throw new RuntimeException("Erro simulado na entidade 6");
//                        }
                    } catch (Exception e) {
                        logger.error("Erro ao processar entidade {}: {}", i, e.getMessage());
                        throw e; // Propagar erro para fazer rollback da transação
                    }
                }

                logger.info("Todas as entidades foram processadas com sucesso");
            } else {
                logger.info("Processo {} não é do tipo BLA, ignorando", nomeProcesso);
            }
        } catch (IOException e) {
            logger.error("Erro ao processar mensagem: {}", e.getMessage());
            throw new RuntimeException("Erro ao processar mensagem", e);
        }
    }
}
