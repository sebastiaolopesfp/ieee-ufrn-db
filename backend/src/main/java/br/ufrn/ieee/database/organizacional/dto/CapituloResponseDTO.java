package br.ufrn.ieee.database.organizacional.dto;

import lombok.Data;

@Data
public class CapituloResponseDTO {
    private String unidadeCodigo;
    private String nome;
    private String email;
    private Integer anoCriacao;
    private String ramoCodigo;
    private String nomeRamo; // Opcional, bom para exibir no frontend
}