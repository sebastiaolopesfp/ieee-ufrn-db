package br.ufrn.ieee.database.academico.dto;

import lombok.Data;

@Data
public class VinculoResponseDTO {
    private Long id;
    private Long voluntarioId;
    private String voluntarioNomeCompleto;
    private String instituicaoNome;
    private String cursoNome;
    private String numMatricula;
    private String emailAcademico;
    private Integer anoIngresso;
    private String statusAcademico;
}