package br.ufrn.ieee.database.financeiro.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.financeiro.model.ItemOrcamento;

import java.util.List;
import java.util.Optional;

public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Long> {

    @Override
    @EntityGraph(attributePaths = { "fornecedor", "evento" })
    Optional<ItemOrcamento> findById(Long id);

    @Override
    @EntityGraph(attributePaths = { "fornecedor", "evento" })
    Page<ItemOrcamento> findAll(Pageable pageable);

    // Retorna todos os itens orçamentários/gastos de um determinado evento
    @EntityGraph(attributePaths = { "fornecedor", "evento" })
    List<ItemOrcamento> findByEventoId(Long eventoId);

    // Filtra os itens por uma categoria financeira (ex: "Alimentação", "Logística")
    // dentro de um evento
    @EntityGraph(attributePaths = { "fornecedor", "evento" })
    List<ItemOrcamento> findByEventoIdAndCategoriaFinanceira(Long eventoId, String categoriaFinanceira);
}