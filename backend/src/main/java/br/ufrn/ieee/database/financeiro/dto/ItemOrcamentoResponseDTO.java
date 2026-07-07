package br.ufrn.ieee.database.financeiro.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemOrcamentoResponseDTO {
    private Long id;
    private Long fornecedorId;
    private String nomeFornecedor;
    private Long eventoId;
    private String descricaoProduto;
    private String categoriaFinanceira;
    private Integer quantidade;
    private BigDecimal custoUnitario;
    private BigDecimal custoTotal; // Campo calculado útil para o Frontend
    private String notaFiscalPath;
    private String linkItem;
}