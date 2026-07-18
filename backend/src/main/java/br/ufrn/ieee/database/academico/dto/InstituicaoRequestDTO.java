package br.ufrn.ieee.database.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class InstituicaoRequestDTO {

    @NotBlank(message = "O nome da instituição é obrigatório")
    @Size(max = 150, message = "O nome da instituição deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "A sigla é obrigatória")
    @Size(max = 20, message = "A sigla deve ter no máximo 20 caracteres")
    private String sigla;

    private List<Long> cursoIds; // opcional — instituição pode ser criada sem cursos vinculados ainda
}