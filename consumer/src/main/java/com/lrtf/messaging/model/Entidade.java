package com.lrtf.messaging.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "entidade")
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String nomeProcesso;
    private LocalDateTime dataHoraInicio;

    public Entidade() {
    }

    public Entidade(String nome, String nomeProcesso) {
        this.nome = nome;
        this.nomeProcesso = nomeProcesso;
        this.dataHoraInicio = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeProcesso() {
        return nomeProcesso;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entidade entidade = (Entidade) o;
        return Objects.equals(id, entidade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entidade{id=" + id + ", nome='" + nome + ", nomeProcesso='" + nomeProcesso + "}";
    }
}