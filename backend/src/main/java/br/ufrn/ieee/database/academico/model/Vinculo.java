package br.ufrn.ieee.database.academico.model;

import br.ufrn.ieee.database.voluntario.model.Voluntario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "vinculo")
public class Vinculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voluntario_id", nullable = false)
    private Voluntario voluntario;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instituicao_id", nullable = false)
    private Instituicao instituicao;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "num_matricula", nullable = false, length = 50)
    private String numMatricula;

    @Column(name = "e_mail_academico", nullable = false, length = 255)
    private String emailAcademico;

    @Column(name = "ano_ingresso", nullable = false)
    private Integer anoIngresso;

    @Column(name = "status_academico", nullable = false, length = 50)
    private String statusAcademico;
}