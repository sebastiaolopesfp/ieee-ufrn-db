package br.ufrn.ieee.database.gestao.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RelatorioRequestDTO {
    private Long diretorId;
    private String unidadeCodigo;
    private String tipoRelatorio;
    private LocalDate dataInicioRelatorio;
    private LocalDate dataFimRelatorio;
    private String relatorioPdfPath;
}