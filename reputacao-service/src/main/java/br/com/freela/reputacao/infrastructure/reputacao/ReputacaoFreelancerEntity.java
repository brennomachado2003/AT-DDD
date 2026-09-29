package br.com.freela.reputacao.infrastructure.reputacao;
import jakarta.persistence.*; import java.math.BigDecimal; import java.util.UUID;
@Entity @Table(name="reputacoes")
public class ReputacaoFreelancerEntity {

 @Id
 private UUID freelancerId;

 private int contratosConcluidos;
 private BigDecimal valorTotal = BigDecimal.ZERO;

 protected ReputacaoFreelancerEntity() {
 }

 public ReputacaoFreelancerEntity(UUID id) {
  this.freelancerId = id;
 }

 public ReputacaoFreelancerEntity(UUID freelancerId, int contratosConcluidos, BigDecimal valorTotal) {
  this.freelancerId = freelancerId;
  this.contratosConcluidos = contratosConcluidos;
  this.valorTotal = valorTotal;
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
