package br.ufrn.ieee.database.organizacional.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "ramo_estudantil")
public class RamoEstudantil {
    @Id
    @Column(name = "unidade_codigo")
    private String unidadeCodigo;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "unidade_codigo", nullable = false)
    private UnidadeOrganizacional unidade;
}