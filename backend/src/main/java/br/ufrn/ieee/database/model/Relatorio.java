package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "relatorio")
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diretor_id", referencedColumnName = "voluntario_id", nullable = false)
    private Diretor director;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "unidade_codigo", referencedColumnName = "codigo", nullable = false)
    private UnidadeOrganizacional unidade;

    @Column(name = "tipo_relatorio", nullable = false, length = 100)
    private String tipoRelatorio;

    @Column(name = "data_geracao", nullable = false)
    private LocalDateTime dataGeracao;

    @Column(name = "data_inicio_relatorio", nullable = false)
    private LocalDate dataInicioRelatorio;

    @Column(name = "data_fim_relatorio", nullable = false)
    private LocalDate dataFimRelatorio;

    @Column(name = "relatorio_pdf_path", nullable = false, length = 255)
    private String relatorioPdfPath;
}