package br.ufrn.ieee.database.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.ufrn.ieee.database.shared.dto.ErroResponseDTO;

// Interceptor global de exceções da camada de apresentação
// Transforma exceções técnicas e de negócio do sistema em respostas HTTP padronizadas
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

    // Trata falhas de autorização em nível de método (RBAC)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponseDTO> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Tentativa de acesso negada: {}", ex.getMessage());
        ErroResponseDTO erro = new ErroResponseDTO(
                "Você não tem permissão para executar esta ação.",
                HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }

    // Trata erros de autenticação disparados pelo AuthenticationManager
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResponseDTO> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Tentativa de login com credenciais inválidas.");
        ErroResponseDTO erro = new ErroResponseDTO("E-mail ou senha inválidos.", HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    // Trata bloqueios de conta gerados por mecanismos preventivos de segurança
    @ExceptionHandler(ContaTemporariamenteBloqueadaException.class)
    public ResponseEntity<ErroResponseDTO> handleContaBloqueada(ContaTemporariamenteBloqueadaException ex) {
        log.warn("Tentativa de login bloqueada por excesso de falhas: {}", ex.getMessage());
        ErroResponseDTO erro = new ErroResponseDTO(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS.value());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(erro);
    }

    // Captura violações de chaves e restrições relacionais mapeadas no banco de
    // dados
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Violação de integridade de dados: {}", ex.getMessage());
        ErroResponseDTO erro = new ErroResponseDTO(
                "Esta operação não pode ser concluída pois o registro está em uso por outra entidade do sistema.",
                HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    // Captura genérica para falhas não mapeadas, agindo como barreira de segurança
    // interna (anti-leaking)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGeneralException(Exception ex) {
        log.error("Erro interno não tratado", ex);
        ErroResponseDTO erro = new ErroResponseDTO(
                "Ocorreu um erro interno no servidor.",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}