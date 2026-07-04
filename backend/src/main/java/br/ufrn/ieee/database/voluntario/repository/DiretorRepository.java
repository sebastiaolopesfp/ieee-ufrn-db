package br.ufrn.ieee.database.voluntario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.voluntario.model.Diretor;

import java.util.Optional;

public interface DiretorRepository extends JpaRepository<Diretor, Long> {
    
    // Busca o diretor através do número de membresia do IEEE dele
    Optional<Diretor> findByMembroNumMembresia(String numMembresia);
}