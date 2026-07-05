package br.ufrn.ieee.database.academico.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContratoRequestDTO {
    private Long vinculoId;
    private String unidadeCodigo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String termoCompromissoPdfPath;
    private String termoDesligamentoPdfPath;
}