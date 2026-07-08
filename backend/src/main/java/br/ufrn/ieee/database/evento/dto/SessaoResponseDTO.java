package br.ufrn.ieee.database.evento.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class SessaoResponseDTO {
    private Long id;
    private Long eventoId;
    private String tituloAtividade;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String local;
    private Set<Long> voluntariosPresentesIds;
}