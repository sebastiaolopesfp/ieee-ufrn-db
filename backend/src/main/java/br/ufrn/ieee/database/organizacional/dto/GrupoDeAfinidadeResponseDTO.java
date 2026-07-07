package br.ufrn.ieee.database.organizacional.dto;

import lombok.Data;

@Data
public class GrupoDeAfinidadeResponseDTO {
    private String unidadeCodigo;
    private String nome;
    private String email;
    private Integer anoCriacao;
    private String ramoCodigo;
    private String nomeRamo;
}