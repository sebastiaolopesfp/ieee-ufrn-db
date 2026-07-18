package br.ufrn.ieee.database.voluntario.dto;

import br.ufrn.ieee.database.voluntario.model.TipoUsuario;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PromoverDiretorRequestDTO {

    // TODO Fase futura: validar que tipoDiretor é DIRETOR_RAMO ou DIRETOR_CAPITULO
    // (não ADMIN/MEMBRO/VOLUNTARIO) exige um @AssertTrue customizado — fora do
    // escopo de blindagem rápida, mas documentado como dívida técnica.
    @NotNull(message = "O tipo de diretor é obrigatório (DIRETOR_RAMO ou DIRETOR_CAPITULO)")
    private TipoUsuario tipoDiretor;

    @NotNull(message = "O cargo é obrigatório")
    private Long cargoId;

    @NotNull(message = "A data de início do mandato é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim do mandato é obrigatória")
    @FutureOrPresent(message = "A data de fim do mandato não pode estar no passado")
    private LocalDate dataFim;
}