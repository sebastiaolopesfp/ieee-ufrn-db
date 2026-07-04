package br.ufrn.ieee.database.voluntario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.voluntario.model.Voluntario;

import java.util.Optional;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
    
    // CRÍTICO PARA O SPRING SECURITY: Usado no processo de login para buscar as credenciais
    Optional<Voluntario> findByEmailPessoal(String emailPessoal);
    
    // Útil para validação de unicidade no cadastro
    Optional<Voluntario> findByCpf(String cpf);
}