package br.ufrn.ieee.database.organizacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;

import java.util.Optional;

public interface UnidadeOrganizacionalRepository extends JpaRepository<UnidadeOrganizacional, String> {
    
    // Busca uma unidade pelo e-mail oficial dela
    Optional<UnidadeOrganizacional> findByEmailIgnoreCase(String email);
}