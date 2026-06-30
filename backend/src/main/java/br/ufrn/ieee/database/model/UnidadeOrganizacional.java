package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unidade_organizacional")
public class UnidadeOrganizacional {
    @Id
    @Column(length = 100)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "e_mail", nullable = false, length = 255)
    private String email;

    @Column(name = "ano_criacao", nullable = false)
    private Integer anoCriacao;
}