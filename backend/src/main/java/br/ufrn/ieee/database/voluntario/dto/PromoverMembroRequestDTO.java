package br.ufrn.ieee.database.voluntario.dto;

import br.ufrn.ieee.database.voluntario.model.TipoMembresia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromoverMembroRequestDTO {

    @NotBlank(message = "O número de membresia IEEE é obrigatório")
    private String numeroMembresia;

    private String emailIeee;

    @NotNull(message = "O tipo de membresia é obrigatório")
    private TipoMembresia tipoMembresia;
}