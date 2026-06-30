package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vinculo_id", nullable = false)
    private Vinculo vinculo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "unidade_codigo", nullable = false)
    private UnidadeOrganizacional unidade;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "termo_compromisso_pdf_path", nullable = false, length = 255)
    private String termoCompromissoPdfPath;

    @Column(name = "termo_desligamento_pdf_path", length = 255)
    private String termoDesligamentoPdfPath;
}