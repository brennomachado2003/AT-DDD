package br.com.freela.reputacao.domain.reputacao;

import java.math.BigDecimal;
import java.util.UUID;

public class ReputacaoFreelancer {

    UUID freelancerId;
    int contratosConcluidos;
    BigDecimal valorTotal = BigDecimal.ZERO;

    public ReputacaoFreelancer(UUID freelancerId, int contratosConcluidos, BigDecimal valorTotal) {
        this.freelancerId = freelancerId;
        this.contratosConcluidos = contratosConcluidos;
        this.valorTotal = valorTotal;
    }

    public ReputacaoFreelancer() {
        this.freelancerId = UUID.randomUUID();
    }

    public static ReputacaoFreelancer restaurar(UUID id, int contratosConcluidos, BigDecimal valorTotal) {
        return new ReputacaoFreelancer(id, contratosConcluidos, valorTotal);
    }

    public void registrarContrato(BigDecimal valor) {
        contratosConcluidos++;
        valorTotal = valorTotal.add(valor);
    }

    public UUID getFreelancerId() {
        return freelancerId;
    }
    public int getContratosConcluidos() {
        return contratosConcluidos;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
