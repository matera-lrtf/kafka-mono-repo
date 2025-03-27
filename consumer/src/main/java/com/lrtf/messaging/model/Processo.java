package com.lrtf.messaging.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "processo")
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @Column(name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    public Processo() {
    }

    public Processo(UUID id, String nome, LocalDateTime dataHoraInicio) {
        this.id = id;
        this.nome = nome;
        this.dataHoraInicio = dataHoraInicio;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Processo processo = (Processo) o;
        return Objects.equals(id, processo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
