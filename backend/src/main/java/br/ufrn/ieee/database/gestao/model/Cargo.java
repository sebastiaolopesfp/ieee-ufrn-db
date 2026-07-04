package br.ufrn.ieee.database.gestao.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;
}