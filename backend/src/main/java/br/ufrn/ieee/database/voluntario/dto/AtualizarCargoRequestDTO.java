package br.ufrn.ieee.database.voluntario.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AtualizarCargoRequestDTO {
    private Long novoCargoId;
    private LocalDate dataFimNovoMandato;
}