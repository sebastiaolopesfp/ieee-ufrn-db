package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.UnidadeOrganizacional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UnidadeOrganizacionalRepository extends JpaRepository<UnidadeOrganizacional, String> {
    
    // Busca uma unidade pelo e-mail oficial dela
    Optional<UnidadeOrganizacional> findByEmailIgnoreCase(String email);
}