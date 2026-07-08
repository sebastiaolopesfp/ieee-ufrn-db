package br.ufrn.ieee.database.evento.dto;

import br.ufrn.ieee.database.evento.model.StatusSincronizacao;
import br.ufrn.ieee.database.evento.model.LocationType;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Data
public class EventoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String vtoolsId;
    private Instant dataInicio;
    private Instant dataFim;
    private LocationType locationType;
    private Boolean published;
    private Boolean reported;
    private String categoria;
    private String subcategoria;
    private Integer qtdMembros;
    private Integer qtdNaoMembros;
    private BigDecimal orcamentoEstimado;
    private StatusSincronizacao statusSincronizacao;
    private Instant dataUltimaSincronizacao;
    private Set<String> unidadesCodigos;
}