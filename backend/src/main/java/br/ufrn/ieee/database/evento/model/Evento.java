package br.ufrn.ieee.database.evento.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;

@Data
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vtools_id", length = 50, unique = true)
    private String vtoolsId;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private Instant dataInicio;

    @Column(name = "data_fim", nullable = false)
    private Instant dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", nullable = false)
    private LocationType locationType;

    @Column(nullable = false)
    private Boolean published = false;

    @Column(nullable = false)
    private Boolean reported = false;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(length = 100)
    private String subcategoria;

    @Column(name = "qtd_membros", nullable = false)
    private Integer qtdMembros = 0;

    @Column(name = "qtd_nao_membros", nullable = false)
    private Integer qtdNaoMembros = 0;

    @Column(name = "orcamento_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal orcamentoEstimado = BigDecimal.ZERO;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "evento_unidade", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "unidade_codigo", referencedColumnName = "unidade_codigo"))
    private Set<UnidadeOrganizacional> unidades = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status_sincronizacao", nullable = false)
    private StatusSincronizacao statusSincronizacao = StatusSincronizacao.LOCAL_APENAS;

    @Column(name = "data_ultima_sincronizacao")
    private Instant dataUltimaSincronizacao;
}