package br.ufrn.ieee.database.voluntario.dto;

import br.ufrn.ieee.database.voluntario.model.TipoMembresia;
import lombok.Data;

@Data
public class PromoverMembroRequestDTO {
    private String numeroMembresia;
    private String emailIeee;
    private TipoMembresia tipoMembresia;
}