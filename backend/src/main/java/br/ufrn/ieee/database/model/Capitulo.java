package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "capitulo")
public class Capitulo {
    @Id
    @Column(name = "unidade_codigo", length = 10)
    private String unidadeCodigo;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "unidade_codigo", nullable = false)
    private UnidadeOrganizacional unidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ramo_codigo", nullable = false)
    private RamoEstudantil ramo;
}