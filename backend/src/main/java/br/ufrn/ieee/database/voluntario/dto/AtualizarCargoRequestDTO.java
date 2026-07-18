package br.ufrn.ieee.database.voluntario.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AtualizarCargoRequestDTO {

    @NotNull(message = "O novo cargo é obrigatório")
    private Long novoCargoId;

    @NotNull(message = "A data de fim do novo mandato é obrigatória")
    @FutureOrPresent(message = "A data de fim do novo mandato não pode estar no passado")
    private LocalDate dataFimNovoMandato;
}