package br.ufrn.ieee.database.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ContratoRequestDTO {

    @NotNull(message = "O vínculo é obrigatório")
    private Long vinculoId;

    @NotBlank(message = "O código da unidade organizacional é obrigatório")
    private String unidadeCodigo;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    private LocalDate dataFim;

    @NotBlank(message = "O caminho do termo de compromisso é obrigatório")
    private String termoCompromissoPdfPath;

    private String termoDesligamentoPdfPath; // opcional — só existe após o desligamento
}