package br.ufrn.ieee.database.voluntario.dto;

import lombok.Data;

@Data
public class VoluntarioResponseDTO {
    private Long id;
    private String primeiroNome;
    private String ultimoNome;
    private String emailPessoal;
    private String tipoUsuario;
    private Boolean ativo;
}