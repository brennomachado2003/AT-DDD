package br.com.freela.contrato.web;

import br.com.freela.contrato.domain.model.Contrato;
import br.com.freela.contrato.infrastructure.persistence.ContratoMapper;
import br.com.freela.contrato.service.*;
import br.com.freela.contrato.dto.ContratoResponse;
import br.com.freela.contrato.dto.CriarContratoRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private static final Logger log = LoggerFactory.getLogger(ContratoController.class);
    private final CriarContratoService criarContratoServiceService;
    private final BuscarContratoService buscarContratoServiceService;
    private final ListarContratoService listarContratoServiceService;
    private final CancelarService cancelarService;
    private final ConcluirService concluirService;
    private final RegistrarEntregaService registrarEntregaService;


    public ContratoController(CriarContratoService criarService, BuscarContratoService buscarService,
                              ListarContratoService listarContratoService, CancelarService cancelarService,
                              RegistrarEntregaService registrarEntregaService, ConcluirService concluirService) {
        this.criarContratoServiceService=criarService;
        this.buscarContratoServiceService=buscarService;
        this.listarContratoServiceService=listarContratoService;
        this.cancelarService=cancelarService;
        this.registrarEntregaService=registrarEntregaService;
        this.concluirService=concluirService;
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ContratoResponse criar(@RequestHeader(value="X-Correlation-Id", required=false) String correlationId,
                                  @Valid @RequestBody CriarContratoRequest request) {
        log.info("http.contrato.criar correlationId={} clienteId={} freelancerId={} titulo={}", correlationId, request.clienteId(), request.freelancerId(), request.titulo());
        Contrato contrato = criarContratoServiceService.criar(ContratoMapper.criarContratoRequestTocriarContratoCommand(request));
        log.info("http.contrato.criar.response correlationId={} contratoId={} status={}", correlationId, contrato.id(), contrato.status());
        return ContratoResponse.from(contrato);
    }

    @GetMapping("/{id}")
    public ContratoResponse buscar(@RequestHeader(value="X-Correlation-Id", required=false) String correlationId, @PathVariable UUID id) {
        log.info("http.contrato.buscar correlationId={} contratoId={}", correlationId, id);
        return ContratoResponse.from(buscarContratoServiceService.buscar(id));
    }

    @GetMapping
    public List<ContratoResponse> listar(@RequestHeader(value="X-Correlation-Id", required=false) String correlationId) {
        log.info("http.contrato.listar correlationId={}", correlationId);
        return listarContratoServiceService.listar().stream().map(ContratoResponse::from).toList();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@RequestHeader(value="X-Correlation-Id", required=false) String correlationId, @PathVariable("id") UUID id) {
        log.info("http.contrato.cancelar correlationId={}", correlationId);
        cancelarService.cancelarContrato(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/concluido")
    public ResponseEntity<Void> concluido(@RequestHeader(value="X-Correlation-Id", required=false) String correlationId, @PathVariable("id") UUID id) {
        log.info("http.contrato.concluir correlationId={}", correlationId);
        concluirService.concluirContrato(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/entregar")
    public ResponseEntity<Void> entregar(@RequestHeader(value="X-Correlation-Id", required=false) String correlationId, @PathVariable("id") UUID id) {
        log.info("http.contrato.entregui correlationId={}", correlationId);
        registrarEntregaService.registrarEntrega(id);
        return ResponseEntity.noContent().build();
    }

}
