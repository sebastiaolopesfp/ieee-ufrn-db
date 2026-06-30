package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    // Útil para validar se o curso digitado já existe no sistema antes de cadastrar
    Optional<Curso> findByNome(String nome);
}