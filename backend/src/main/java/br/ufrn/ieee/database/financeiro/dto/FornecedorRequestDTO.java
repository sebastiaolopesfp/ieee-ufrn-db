package br.ufrn.ieee.database.financeiro.dto;

import lombok.Data;

@Data
public class FornecedorRequestDTO {
    private String nome;
    private String telefone;
    private String linkWebsite;
}