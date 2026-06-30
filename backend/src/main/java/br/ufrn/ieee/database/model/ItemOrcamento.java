package br.ufrn.ieee.database.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item_orcamento")
public class ItemOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(name = "descricao_produto", nullable = false, length = 255)
    private String descricaoProduto;

    @Column(name = "categoria_financeira", nullable = false, length = 100)
    private String categoriaFinanceira;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "custo_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal custoUnitario;

    @Column(name = "nota_fiscal_path", nullable = false, length = 255)
    private String notaFiscalPath;

    @Column(name = "link_item", length = 512)
    private String linkItem;
}