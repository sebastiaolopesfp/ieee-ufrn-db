package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "membro")
public class Membro {
    @Id
    @Column(name = "voluntario_id")
    private Long voluntarioId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Voluntario voluntario;

    @Column(name = "num_membresia", nullable = false, unique = true, length = 20)
    private String numMembresia;

    @Column(name = "tipo_membresia", nullable = false, length = 50)
    private String tipoMembresia;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "e_mail_ieee", nullable = false, unique = true, length = 255)
    private String emailIeee;
}