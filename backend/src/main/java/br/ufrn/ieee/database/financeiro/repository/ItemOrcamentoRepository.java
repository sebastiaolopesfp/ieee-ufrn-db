package br.ufrn.ieee.database.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.financeiro.model.ItemOrcamento;

import java.util.List;

public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Long> {
    
    // Retorna todos os itens orçamentários/gastos de um determinado evento
    List<ItemOrcamento> findByEventoId(Long eventoId);
    
    // Filtra os itens por uma categoria financeira (ex: "Alimentação", "Logística") dentro de um evento
    List<ItemOrcamento> findByEventoIdAndCategoriaFinanceira(Long eventoId, String categoriaFinanceira);
}