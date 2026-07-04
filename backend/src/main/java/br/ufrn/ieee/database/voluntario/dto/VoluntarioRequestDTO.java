package br.ufrn.ieee.database.voluntario.dto;

import lombok.Data;

@Data
public class VoluntarioRequestDTO {
    private String primeiroNome;
    private String nomeMeio;
    private String ultimoNome;
    private String senha;
    private String emailPessoal;
    private String telefone;
    private String cpf;
}