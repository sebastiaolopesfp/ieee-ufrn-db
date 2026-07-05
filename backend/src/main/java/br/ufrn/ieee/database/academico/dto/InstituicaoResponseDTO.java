package br.ufrn.ieee.database.academico.dto;

import lombok.Data;

import java.util.List;

@Data
public class InstituicaoResponseDTO {
    private Long id;
    private String nome;
    private String sigla;
    private List<CursoResponseDTO> cursos;
}