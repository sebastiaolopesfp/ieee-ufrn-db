package br.ufrn.ieee.database.gestao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RelatorioRequestDTO {

    @NotNull(message = "O diretor responsável é obrigatório")
    private Long diretorId;

    @NotBlank(message = "O código da unidade organizacional é obrigatório")
    private String unidadeCodigo;

    @NotBlank(message = "O tipo de relatório é obrigatório")
    private String tipoRelatorio;

    @NotNull(message = "A data de início do período do relatório é obrigatória")
    private LocalDate dataInicioRelatorio;

    @NotNull(message = "A data de fim do período do relatório é obrigatória")
    private LocalDate dataFimRelatorio;

    @NotBlank(message = "O caminho do PDF do relatório é obrigatório")
    private String relatorioPdfPath;
}