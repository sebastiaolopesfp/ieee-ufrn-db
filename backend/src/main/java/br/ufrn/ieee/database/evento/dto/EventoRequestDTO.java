package br.ufrn.ieee.database.evento.dto;

import br.ufrn.ieee.database.evento.model.LocationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Data
public class EventoRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private String vtoolsId; // opcional — só preenchido em eventos importados

    @NotNull(message = "A data de início é obrigatória")
    private Instant dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    private Instant dataFim;

    private LocationType locationType;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    private String subcategoria;

    @PositiveOrZero(message = "O orçamento estimado não pode ser negativo")
    private BigDecimal orcamentoEstimado;

    private Set<String> unidadesCodigos;

    @PositiveOrZero(message = "A quantidade de membros não pode ser negativa")
    private Integer qtdMembros;

    @PositiveOrZero(message = "A quantidade de não membros não pode ser negativa")
    private Integer qtdNaoMembros;
}