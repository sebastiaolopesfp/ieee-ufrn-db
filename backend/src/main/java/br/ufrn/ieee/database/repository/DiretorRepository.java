package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DiretorRepository extends JpaRepository<Diretor, Long> {
    
    // Busca o diretor através do número de membresia do IEEE dele
    Optional<Diretor> findByMembroNumMembresia(String numMembresia);
}