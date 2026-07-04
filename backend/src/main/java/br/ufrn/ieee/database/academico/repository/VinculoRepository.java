package br.ufrn.ieee.database.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.academico.model.Vinculo;

import java.util.List;

public interface VinculoRepository extends JpaRepository<Vinculo, Long> {
    
    // Busca todos os vínculos acadêmicos de um voluntário específico
    List<Vinculo> findByVoluntarioId(Long voluntarioId);
    
    // Filtra os vínculos por instituição (ex: listar todos os alunos da UFRN no sistema)
    List<Vinculo> findByInstituicaoId(Long coding);
}