package br.ufrn.ieee.database.organizacional.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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
}