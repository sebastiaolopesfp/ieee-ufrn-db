package br.ufrn.ieee.database.organizacional.dto;

import lombok.Data;

@Data
public class RamoEstudantilResponseDTO {
    private String unidadeCodigo;
    private String nome;
    private String email;
    private Integer anoCriacao;
}