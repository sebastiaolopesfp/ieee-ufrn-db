package br.ufrn.ieee.database.evento.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import br.ufrn.ieee.database.voluntario.model.Voluntario;

@Data
@Entity
@Table(name = "sessao")
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @Column(name = "titulo_atividade", nullable = false, length = 255)
    private String tituloAtividade;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(nullable = false, length = 150)
    private String local;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "voluntario_sessao", joinColumns = @JoinColumn(name = "sessao_id"), inverseJoinColumns = @JoinColumn(name = "voluntario_id"))
    private Set<Voluntario> voluntarios = new HashSet<>();
}