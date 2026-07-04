package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "instituicao")
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 20)
    private String sigla;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "instituicao_curso",
        joinColumns = @JoinColumn(name = "instituicao_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos;
}