package br.ufrn.ieee.database.organizacional.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ramo_estudantil")
public class RamoEstudantil {
    @Id
    @Column(name = "unidade_codigo")
    private String unidadeCodigo;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "unidade_codigo", nullable = false)
    private UnidadeOrganizacional unidade;
}