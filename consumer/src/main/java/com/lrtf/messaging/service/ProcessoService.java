package com.lrtf.messaging.service;

import com.lrtf.messaging.model.Entidade;
import com.lrtf.messaging.model.Processo;
import com.lrtf.messaging.model.EntidadeOutbox;
import com.lrtf.messaging.repository.EntidadeOutboxRepository;
import com.lrtf.messaging.repository.EntidadeRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessoService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessoService.class);

    private final EntidadeRepository entidadeRepository;
    private final EntidadeOutboxRepository entidadeOutboxRepository;

    private ProcessoService(
        EntidadeRepository processoRepository,
        EntidadeOutboxRepository processoOutboxRepository) {

        this.entidadeRepository = processoRepository;
        this.entidadeOutboxRepository = processoOutboxRepository;
    }

    @Transactional
    public void salvar(Entidade entidade) {
        logger.info("Salvando entidade {}", entidade);
        Entidade novaEntidade = entidadeRepository.save(entidade);

        EntidadeOutbox entidadeOutbox = new EntidadeOutbox().converteEntidade(novaEntidade);
        
        logger.info("Salvando entidade no outbox: {}", entidadeOutbox);
        entidadeOutboxRepository.save(entidadeOutbox);

        logger.info("Entidade salva com sucesso");
    }

    @Transactional
    public void salvar(List<Entidade> entidades) {

        logger.info("Salvando {} entidades: ", entidades.size());
        List<Entidade> novasEntidades = entidadeRepository.saveAll(entidades);

        List<EntidadeOutbox> entidadesOutbox = new EntidadeOutbox().converteEntidades(novasEntidades);


        logger.info("Salvando entidades no outbox: {}", entidadesOutbox);
        entidadeOutboxRepository.saveAll(entidadesOutbox);

        logger.info("Processo salvo com sucesso");
    }

}
