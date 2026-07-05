package br.ufrn.ieee.database.gestao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MandatoResponseDTO {
    private Long id;
    private Long cargoId;
    private String nomeCargo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean ativo;
}