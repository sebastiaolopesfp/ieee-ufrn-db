package br.ufrn.ieee.database.voluntario.dto;

import lombok.Data;

@Data
public class AlterarSenhaRequestDTO {
    private String senhaAtual;
    private String novaSenha;
}