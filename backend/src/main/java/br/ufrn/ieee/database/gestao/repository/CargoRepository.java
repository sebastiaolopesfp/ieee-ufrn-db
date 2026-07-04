package br.ufrn.ieee.database.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.gestao.model.Cargo;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    
    // Útil para buscar o cargo por nome ao associar a um mandato (ex: "Presidente", "Tesoureiro")
    Optional<Cargo> findByNome(String nome);
}