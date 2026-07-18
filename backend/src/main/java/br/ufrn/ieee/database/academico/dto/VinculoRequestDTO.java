package br.ufrn.ieee.database.academico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VinculoRequestDTO {

    @NotNull(message = "O voluntário é obrigatório")
    private Long voluntarioId;

    @NotNull(message = "A instituição é obrigatória")
    private Long instituicaoId;

    @NotNull(message = "O curso é obrigatório")
    private Long cursoId;

    @NotBlank(message = "O número de matrícula é obrigatório")
    @Size(max = 50, message = "O número de matrícula deve ter no máximo 50 caracteres")
    private String numMatricula;

    @NotBlank(message = "O e-mail acadêmico é obrigatório")
    @Email(message = "O e-mail acadêmico informado não é válido")
    private String emailAcademico;

    @NotNull(message = "O ano de ingresso é obrigatório")
    @Min(value = 2000, message = "O ano de ingresso deve ser posterior a 2000")
    private Integer anoIngresso;

    @NotBlank(message = "O status acadêmico é obrigatório")
    private String statusAcademico;
}