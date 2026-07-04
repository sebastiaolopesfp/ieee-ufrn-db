package br.ufrn.ieee.database.shared.dto;

import java.time.Instant;

public record ErroResponseDTO(
    String mensagem,
    int status,
    Instant timestamp
) {
    public ErroResponseDTO(String mensagem, int status) {
        this(mensagem, status, Instant.now());
    }
}