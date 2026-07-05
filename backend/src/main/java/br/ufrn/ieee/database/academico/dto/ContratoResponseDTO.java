package br.ufrn.ieee.database.academico.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContratoResponseDTO {
    private Long id;
    private Long vinculoId;
    private String voluntarioNomeCompleto;
    private String unidadeCodigo;
    private String unidadeNome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String termoCompromissoPdfPath;
    private String termoDesligamentoPdfPath;
}