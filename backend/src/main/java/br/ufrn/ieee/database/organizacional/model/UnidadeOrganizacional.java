package br.ufrn.ieee.database.organizacional.model;

import java.util.HashSet;
import java.util.Set;

import br.ufrn.ieee.database.evento.model.Evento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "unidade_organizacional")
public class UnidadeOrganizacional {
    @Id
    @Column(name = "unidade_codigo", length = 10)
    private String unidadeCodigo;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "e_mail", nullable = false, length = 255)
    private String email;

    @Column(name = "ano_criacao", nullable = false)
    private Integer anoCriacao;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "unidades")
    private Set<Evento> eventos = new HashSet<>();
}