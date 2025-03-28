package com.lrtf.messaging.repository;

import com.lrtf.messaging.model.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EntidadeRepository extends JpaRepository<Entidade, UUID> {
}
