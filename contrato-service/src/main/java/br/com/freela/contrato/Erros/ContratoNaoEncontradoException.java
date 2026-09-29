package br.com.freela.contrato.Erros;

import java.util.UUID;

public class ContratoNaoEncontradoException extends RuntimeException {

    public ContratoNaoEncontradoException(UUID id) {
        super("Contrato não encontrado: " + id);
    }
}
