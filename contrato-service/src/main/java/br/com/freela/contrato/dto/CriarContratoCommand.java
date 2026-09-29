package br.com.freela.contrato.dto;
import java.math.BigDecimal;
import java.util.UUID;
public record CriarContratoCommand(
        UUID clienteId,
        UUID freelancerId,
        String titulo,
        BigDecimal valor) {}
