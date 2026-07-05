package br.ufrn.ieee.database.academico.dto;

import lombok.Data;

@Data
public class VinculoRequestDTO {
    private Long voluntarioId;
    private Long instituicaoId;
    private Long cursoId;
    private String numMatricula;
    private String emailAcademico;
    private Integer anoIngresso;
    private String statusAcademico;
}