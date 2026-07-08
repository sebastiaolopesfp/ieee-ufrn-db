package br.ufrn.ieee.database.evento.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessaoRequestDTO {
    private Long eventoId;
    private String tituloAtividade;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String local;
}