package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ramo_estudantil")
public class RamoEstudantil {
    @Id
    @Column(name = "unidade_codigo")
    private String unidadeCodigo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "unidade_codigo")
    private UnidadeOrganizacional unidade;
}