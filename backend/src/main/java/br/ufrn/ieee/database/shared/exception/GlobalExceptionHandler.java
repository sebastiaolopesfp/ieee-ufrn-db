package br.ufrn.ieee.database.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.ufrn.ieee.database.shared.dto.ErroResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    // Disparada pelas anotações @PreAuthorize quando o usuário autenticado não
    // possui a Role necessária para acessar o recurso (RBAC)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponseDTO> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Tentativa de acesso negada: {}", ex.getMessage());
        ErroResponseDTO erro = new ErroResponseDTO(
                "Você não tem permissão para executar esta ação.",
                HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }

    // Disparada quando o banco rejeita a operação por violação de restrição de
    // integridade
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Violação de integridade de dados: {}", ex.getMessage());
        ErroResponseDTO erro = new ErroResponseDTO(
                "Esta operação não pode ser concluída pois o registro está em uso por outra entidade do sistema.",
                HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    // Fallback para qualquer erro inesperado no sistema
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGeneralException(Exception ex) {
        log.error("Erro interno não tratado", ex);
        ErroResponseDTO erro = new ErroResponseDTO(
                "Ocorreu um erro interno no servidor.",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}