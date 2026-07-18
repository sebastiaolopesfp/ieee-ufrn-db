package br.ufrn.ieee.database.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail informado não é válido")
    private String emailPessoal;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}