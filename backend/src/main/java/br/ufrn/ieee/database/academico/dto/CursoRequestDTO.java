package br.ufrn.ieee.database.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CursoRequestDTO {

    @NotBlank(message = "O nome do curso é obrigatório")
    @Size(max = 150, message = "O nome do curso deve ter no máximo 150 caracteres")
    private String nome;
}