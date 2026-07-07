package br.ufrn.ieee.database.organizacional.dto;

import lombok.Data;

@Data
public class CapituloRequestDTO {
    // Dados da Unidade Organizacional base
    private String unidadeCodigo; // Ex: "SBC-100"
    private String nome;
    private String email;
    private Integer anoCriacao;
    
    // Vínculo específico do Capítulo
    private String ramoCodigo;
}