package br.com.freela.notificacao.domain;

import java.time.Instant;
import java.util.UUID;


public class Notificacao {

    private UUID id;
    private UUID eventId;
    private UUID contratoId;
    private UUID destinatarioId;
    private String tipo;
    private String menssagem;
    Instant criadaEm;

    public Notificacao(UUID id, UUID eventId,UUID contratoId, UUID destinatarioId, String tipo, String menssagem) {
        this.id = id;
        this.eventId = eventId;
        this.contratoId = contratoId;
        this.destinatarioId = destinatarioId;
        this.tipo = tipo;
        this.menssagem = menssagem;
        this.criadaEm = Instant.now();
    }

    public Notificacao(UUID id, UUID contratoId, UUID destinatarioId, String tipo, String menssagem, Instant criadaEm) {
        this.id = id;
        this.contratoId = contratoId;
        this.destinatarioId = destinatarioId;
        this.tipo = tipo;
        this.menssagem = menssagem;
        this.criadaEm = criadaEm;
    }

    public static Notificacao criar(UUID eventId,UUID contratoId, UUID destinatarioId, String tipo, String menssagem) {
        if (contratoId == null || destinatarioId == null) throw new IllegalArgumentException("Contrato e destinatario são obrigatórios");
        if (tipo == null || tipo.isBlank()) throw new IllegalArgumentException("Tipo é obrigatório");
        if (menssagem == null || menssagem.isBlank()) throw new IllegalArgumentException("Menssagem é obrigatório");
        return new Notificacao(UUID.randomUUID(), eventId, contratoId, destinatarioId, tipo.trim(), menssagem.trim());
    }

    public static Notificacao restaurar(UUID id, UUID contratoId, UUID destinatarioId, String tipo, String menssagem, Instant criadaEm) {
        return new Notificacao(id, contratoId, destinatarioId, tipo, menssagem, criadaEm);
    }

    public UUID getId() {
        return id;
    }
    public UUID getContratoId() {
        return contratoId;
    }
    public UUID getDestinatarioId() {
        return destinatarioId;
    }
    public String getTipo() {
        return tipo;
    }
    public String getMenssagem() {
        return menssagem;
    }
    public Instant getCriadaEm() {
        return criadaEm;
    }

    public UUID getEventId() {
        return eventId;
    }
}
