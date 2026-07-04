package br.ufrn.ieee.database.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.financeiro.model.Fornecedor;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    
    // Busca fornecedores pelo nome ou parte dele (útil para autocomplete no painel)
    List<Fornecedor> findByNomeContainingIgnoreCase(String nome);
}