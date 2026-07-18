package br.ufrn.ieee.database.voluntario.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class AdminUpdateEmailCPFRequestDTO {

    @Email(message = "O novo e-mail informado não é válido")
    private String novoEmail;

    @CPF(message = "O novo CPF informado não é válido")
    private String novoCpf;
}