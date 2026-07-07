package br.ufrn.ieee.database.financeiro.dto;

import lombok.Data;

@Data
public class FornecedorResponseDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String linkWebsite;
}