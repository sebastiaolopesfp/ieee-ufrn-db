package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MembroRepository extends JpaRepository<Membro, Long> {
    
    // Busca o membro usando o número de membresia do IEEE
    Optional<Membro> findByNumMembresia(String numMembresia);
    
    // Busca o membro pelo e-mail corporativo do IEEE
    Optional<Membro> findByEmailIeeeIgnoreCase(String emailIeee);
}