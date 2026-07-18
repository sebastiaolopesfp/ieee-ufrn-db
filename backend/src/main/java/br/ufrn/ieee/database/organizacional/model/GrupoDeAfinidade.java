package br.ufrn.ieee.database.organizacional.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "grupo_de_afinidade")
public class GrupoDeAfinidade {
    @Id
    @Column(name = "unidade_codigo", length = 10)
    private String unidadeCodigo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "unidade_codigo", nullable = false)
    private UnidadeOrganizacional unidade;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ramo_codigo", nullable = false)
    private RamoEstudantil ramo;
}