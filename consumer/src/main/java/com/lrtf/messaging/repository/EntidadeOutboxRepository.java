package com.lrtf.messaging.repository;

import com.lrtf.messaging.model.EntidadeOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EntidadeOutboxRepository extends JpaRepository<EntidadeOutbox, UUID> {
}
