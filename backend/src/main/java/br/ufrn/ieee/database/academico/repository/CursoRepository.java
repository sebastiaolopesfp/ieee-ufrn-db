package br.ufrn.ieee.database.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.academico.model.Curso;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    
    // Útil para validar se o curso digitado já existe no sistema antes de cadastrar
    Optional<Curso> findByNome(String nome);
}