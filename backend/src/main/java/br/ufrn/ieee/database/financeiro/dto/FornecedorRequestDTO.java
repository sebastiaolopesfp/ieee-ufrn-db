package br.ufrn.ieee.database.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FornecedorRequestDTO {

    @NotBlank(message = "O nome do fornecedor é obrigatório")
    @Size(max = 150, message = "O nome do fornecedor deve ter no máximo 150 caracteres")
    private String nome;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @Size(max = 512, message = "O link do site deve ter no máximo 512 caracteres")
    private String linkWebsite;
}