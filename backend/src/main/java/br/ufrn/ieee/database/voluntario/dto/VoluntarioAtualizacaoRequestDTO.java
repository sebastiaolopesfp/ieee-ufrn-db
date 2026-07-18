package br.ufrn.ieee.database.voluntario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VoluntarioAtualizacaoRequestDTO {

    @NotBlank(message = "O primeiro nome é obrigatório")
    @Size(max = 50, message = "O primeiro nome deve ter no máximo 50 caracteres")
    private String primeiroNome;

    @Size(max = 50, message = "O nome do meio deve ter no máximo 50 caracteres")
    private String nomeMeio;

    @NotBlank(message = "O último nome é obrigatório")
    @Size(max = 50, message = "O último nome deve ter no máximo 50 caracteres")
    private String ultimoNome;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;
}