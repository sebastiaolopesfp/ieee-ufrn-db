package br.ufrn.ieee.database.evento.dto;

import br.ufrn.ieee.database.evento.model.LocationType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Data
public class EventoRequestDTO {
    private String titulo;
    private String descricao;
    private String vtoolsId;
    private Instant dataInicio;
    private Instant dataFim;
    private LocationType locationType;
    private String categoria;
    private String subcategoria;
    private BigDecimal orcamentoEstimado;
    private Set<String> unidadesCodigos;
}