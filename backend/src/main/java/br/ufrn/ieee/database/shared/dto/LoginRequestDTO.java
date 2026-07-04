package br.ufrn.ieee.database.shared.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String emailPessoal;
    private String senha;
}