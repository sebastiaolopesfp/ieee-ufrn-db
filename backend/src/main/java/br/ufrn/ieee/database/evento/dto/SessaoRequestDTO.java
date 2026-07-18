package br.ufrn.ieee.database.evento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessaoRequestDTO {

    @NotNull(message = "O evento é obrigatório")
    private Long eventoId;

    @NotBlank(message = "O título da atividade é obrigatório")
    private String tituloAtividade;

    @NotNull(message = "A data é obrigatória")
    private LocalDate data;

    @NotNull(message = "A hora de início é obrigatória")
    private LocalTime horaInicio;

    @NotNull(message = "A hora de fim é obrigatória")
    private LocalTime horaFim;

    @NotBlank(message = "O local é obrigatório")
    private String local;
}