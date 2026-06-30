package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "voluntario")
public class Voluntario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "primeiro_nome", nullable = false, length = 50)
    private String primeiroNome;

    @Column(name = "nome_meio", length = 50)
    private String nomeMeio;

    @Column(name = "ultimo_nome", nullable = false, length = 50)
    private String ultimoNome;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(name = "e_mail_pessoal", nullable = false, unique = true, length = 255)
    private String emailPessoal;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "tipo_usuario", nullable = false, length = 30)
    private String tipoUsuario = "VOLUNTARIO";

    @OneToOne(mappedBy = "voluntario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Membro membro;

    @OneToMany(mappedBy = "voluntario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vinculo> vinculos = new ArrayList<>();
}