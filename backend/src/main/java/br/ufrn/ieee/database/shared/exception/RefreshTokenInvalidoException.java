package br.ufrn.ieee.database.shared.exception;

public class RefreshTokenInvalidoException extends RuntimeException {
    public RefreshTokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}