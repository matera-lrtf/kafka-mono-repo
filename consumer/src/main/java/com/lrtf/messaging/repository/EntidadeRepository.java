package com.lrtf.messaging.repository;

import com.lrtf.messaging.model.Entidade;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadeRepository extends JpaRepository<Entidade, UUID> {

}
