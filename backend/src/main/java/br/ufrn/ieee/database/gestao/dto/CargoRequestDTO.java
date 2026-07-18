package br.ufrn.ieee.database.gestao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CargoRequestDTO {

    @NotBlank(message = "O nome do cargo é obrigatório")
    @Size(max = 50, message = "O nome do cargo deve ter no máximo 50 caracteres")
    private String nome;
}