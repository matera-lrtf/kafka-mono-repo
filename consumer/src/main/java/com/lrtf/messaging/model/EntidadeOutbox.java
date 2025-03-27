package com.lrtf.messaging.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Entity(name = "entidade_outbox")
public class EntidadeOutbox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private String nome;
    @Column(name = "nome_processo")
    private String nomeProcesso;
    @Column(name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;
    @Column(columnDefinition = "jsonb")
    private String payload;

    public EntidadeOutbox() {
    }

    public EntidadeOutbox converteEntidade(Entidade entidade) {
        this.id = entidade.getId();
        this.nome = entidade.getNome();
        this.nomeProcesso = entidade.getNomeProcesso();
        this.dataHoraInicio = entidade.getDataHoraInicio();
        this.payload = convertToJson(entidade);
        return this;
    }

    private String convertToJson(Entidade entidade) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(entidade);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter entidade para JSON", e);
        }
    }

    public List<EntidadeOutbox> converteEntidades(List<Entidade> entidades) {
        return entidades.stream()
            .map(this::converteEntidade)
            .collect(Collectors.toList());
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntidadeOutbox processo = (EntidadeOutbox) o;
        return Objects.equals(id, processo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EntidadeOutbox{id=" + id + ", nome='" + nome + ", nomeProcesso='" + nomeProcesso + "'}";
    }
}
