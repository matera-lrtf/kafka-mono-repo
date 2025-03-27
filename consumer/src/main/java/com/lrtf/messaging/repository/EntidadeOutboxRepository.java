package com.lrtf.messaging.repository;

import com.lrtf.messaging.model.EntidadeOutbox;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadeOutboxRepository extends JpaRepository<EntidadeOutbox, UUID> {

}
