package br.ufrn.ieee.database.financeiro.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemOrcamentoRequestDTO {
    private Long fornecedorId;
    private Long eventoId;
    private String descricaoProduto;
    private String categoriaFinanceira;
    private Integer quantidade;
    private BigDecimal custoUnitario;
    private String notaFiscalPath;
    private String linkItem;
}