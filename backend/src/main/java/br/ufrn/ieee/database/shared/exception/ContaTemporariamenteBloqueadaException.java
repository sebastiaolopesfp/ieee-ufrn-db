package br.ufrn.ieee.database.shared.exception;

public class ContaTemporariamenteBloqueadaException extends RuntimeException {
    public ContaTemporariamenteBloqueadaException(String mensagem) {
        super(mensagem);
    }
}