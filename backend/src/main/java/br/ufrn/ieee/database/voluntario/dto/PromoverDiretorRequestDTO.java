package br.ufrn.ieee.database.voluntario.dto;

import br.ufrn.ieee.database.voluntario.model.TipoUsuario;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PromoverDiretorRequestDTO {
    private TipoUsuario tipoDiretor; // Deve ser DIRETOR_RAMO ou DIRETOR_CAPITULO
    private Long cargoId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}