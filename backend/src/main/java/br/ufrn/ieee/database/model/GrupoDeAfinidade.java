package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "grupo_de_afinidade")
public class GrupoDeAfinidade {
    @Id
    @Column(name = "unidade_codigo")
    private String unidadeCodigo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "unidade_codigo")
    private UnidadeOrganizacional unidade;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ramo_codigo", nullable = false)
    private RamoEstudantil ramo;
}