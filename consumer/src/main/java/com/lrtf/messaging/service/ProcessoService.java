package com.lrtf.messaging.service;

import com.lrtf.messaging.model.Entidade;
import com.lrtf.messaging.model.EntidadeOutbox;
import com.lrtf.messaging.repository.EntidadeOutboxRepository;
import com.lrtf.messaging.repository.EntidadeRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(transactionManager = "transactionManager")
public class ProcessoService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessoService.class);

    private final EntidadeRepository entidadeRepository;
    private final EntidadeOutboxRepository entidadeOutboxRepository;

    public ProcessoService(EntidadeRepository entidadeRepository, 
                         EntidadeOutboxRepository entidadeOutboxRepository) {
        this.entidadeRepository = entidadeRepository;
        this.entidadeOutboxRepository = entidadeOutboxRepository;
    }

    @Transactional(transactionManager = "transactionManager")
    public void salvar(Entidade entidade) {
        logger.info("Salvando entidade {}", entidade);
        Entidade novaEntidade = entidadeRepository.save(entidade);

        EntidadeOutbox entidadeOutbox = new EntidadeOutbox().converteEntidade(novaEntidade);
        
        logger.info("Salvando entidade no outbox: {}", entidadeOutbox);
        entidadeOutboxRepository.save(entidadeOutbox);

        logger.info("Entidade salva com sucesso");
    }

    @Transactional(transactionManager = "transactionManager")
    public void salvar(List<Entidade> entidades) {

        logger.info("Salvando {} entidades: ", entidades.size());
        List<Entidade> novasEntidades = entidadeRepository.saveAll(entidades);

        List<EntidadeOutbox> entidadesOutbox = new EntidadeOutbox().converteEntidades(novasEntidades);


        logger.info("Salvando entidades no outbox: {}", entidadesOutbox);
        entidadeOutboxRepository.saveAll(entidadesOutbox);

        logger.info("Processo salvo com sucesso");
    }

    public void processarEntidade(String nomeProcesso, int numeroEntidade) {
        logger.info("Processando entidade {} do processo {}", numeroEntidade, nomeProcesso);
        
        // Criar nova entidade
        Entidade entidade = new Entidade(
            "Entidade " + numeroEntidade,
            nomeProcesso
        );

        // Salvar entidade
        entidade = entidadeRepository.save(entidade);
        logger.info("Entidade salva com ID: {}", entidade.getId());
        
        // Criar e salvar entidade outbox
        EntidadeOutbox entidadeOutbox = new EntidadeOutbox();
        entidadeOutbox.converteEntidade(entidade);
        
        entidadeOutboxRepository.save(entidadeOutbox);
        
        logger.info("Entidade {} processada com sucesso", entidade.getId());
    }

}
