package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    
    // Busca fornecedores pelo nome ou parte dele (útil para autocomplete no painel)
    List<Fornecedor> findByNomeContainingIgnoreCase(String nome);
}