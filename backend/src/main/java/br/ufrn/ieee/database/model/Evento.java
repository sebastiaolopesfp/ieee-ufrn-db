package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "vtools_id", length = 50)
    private String vtoolsId;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(length = 100)
    private String subcategoria;

    @Column(name = "qtd_membros", nullable = false)
    private Integer qtdMembros;

    @Column(name = "qtd_nao_membros", nullable = false)
    private Integer qtdNaoMembros;

    @Column(name = "orcamento_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal orcamentoEstimado;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "evento_unidade",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "unidade_codigo", referencedColumnName = "codigo")
    )
    private Set<UnidadeOrganizacional> unidades = new HashSet<>();
}