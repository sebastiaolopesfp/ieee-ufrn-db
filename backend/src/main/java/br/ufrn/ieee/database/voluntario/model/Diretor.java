package br.ufrn.ieee.database.voluntario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "membro" })
@Entity
@Table(name = "diretor")
public class Diretor {
    @Id
    @Column(name = "voluntario_id")
    private Long voluntarioId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Membro membro;
}