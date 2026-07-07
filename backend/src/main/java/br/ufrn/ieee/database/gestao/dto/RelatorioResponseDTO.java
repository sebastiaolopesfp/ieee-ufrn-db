package br.ufrn.ieee.database.gestao.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RelatorioResponseDTO {
    private Long id;
    private Long diretorId;
    private String unidadeCodigo;
    private String tipoRelatorio;
    private LocalDateTime dataGeracao;
    private LocalDate dataInicioRelatorio;
    private LocalDate dataFimRelatorio;
    private String relatorioPdfPath;
}