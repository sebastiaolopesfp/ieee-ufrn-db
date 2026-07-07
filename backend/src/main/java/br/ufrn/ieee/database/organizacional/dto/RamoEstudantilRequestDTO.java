package br.ufrn.ieee.database.organizacional.dto;

import lombok.Data;

@Data
public class RamoEstudantilRequestDTO {
    private String unidadeCodigo;
    private String nome;
    private String email;
    private Integer anoCriacao;
}