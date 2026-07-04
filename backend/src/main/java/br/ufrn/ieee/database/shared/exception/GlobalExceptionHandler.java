package br.ufrn.ieee.database.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.ufrn.ieee.database.shared.dto.ErroResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponseDTO> handleRegraDeNegocio(RegraDeNegocioException ex) {
        ErroResponseDTO erro = new ErroResponseDTO(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroResponseDTO> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        ErroResponseDTO erro = new ErroResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Caso aconteça qualquer outro erro inesperado no sistema
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGeneralException(Exception ex) {
        ErroResponseDTO erro = new ErroResponseDTO("Ocorreu um erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}