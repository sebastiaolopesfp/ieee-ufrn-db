package br.ufrn.ieee.database.financeiro.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemOrcamentoRequestDTO {

    @NotNull(message = "O fornecedor é obrigatório")
    private Long fornecedorId;

    @NotNull(message = "O evento é obrigatório")
    private Long eventoId;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(max = 255, message = "A descrição do produto deve ter no máximo 255 caracteres")
    private String descricaoProduto;

    @NotBlank(message = "A categoria financeira é obrigatória")
    @Size(max = 100, message = "A categoria financeira deve ter no máximo 100 caracteres")
    private String categoriaFinanceira;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    private Integer quantidade;

    @NotNull(message = "O custo unitário é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O custo unitário deve ser maior que zero")
    private BigDecimal custoUnitario;

    @NotBlank(message = "O caminho da nota fiscal é obrigatório")
    private String notaFiscalPath;

    @Size(max = 512, message = "O link do item deve ter no máximo 512 caracteres")
    private String linkItem;
}