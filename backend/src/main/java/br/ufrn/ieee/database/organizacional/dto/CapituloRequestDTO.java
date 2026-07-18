package br.ufrn.ieee.database.organizacional.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CapituloRequestDTO {

    @NotBlank(message = "O código da unidade é obrigatório")
    @Size(max = 10, message = "O código da unidade deve ter no máximo 10 caracteres")
    private String unidadeCodigo;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail informado não é válido")
    private String email;

    @NotNull(message = "O ano de criação é obrigatório")
    @Min(value = 1963, message = "Ano de criação inválido")
    private Integer anoCriacao;

    @NotBlank(message = "O código do ramo estudantil ao qual este capítulo pertence é obrigatório")
    private String ramoCodigo;
}