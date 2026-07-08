package br.ufrn.ieee.database.voluntario.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = { "voluntario", "diretor" })
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_membresia", nullable = false, length = 50)
    private TipoMembresia tipoMembresia;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "e_mail_ieee", nullable = false, unique = true, length = 255)
    private String emailIeee;

    @OneToOne(mappedBy = "membro", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Diretor diretor;
}