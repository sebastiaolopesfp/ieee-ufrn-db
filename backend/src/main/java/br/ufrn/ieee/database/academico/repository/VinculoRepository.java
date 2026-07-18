package br.ufrn.ieee.database.academico.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.academico.model.Vinculo;

import java.util.List;
import java.util.Optional;

public interface VinculoRepository extends JpaRepository<Vinculo, Long> {

    @Override
    @EntityGraph(attributePaths = { "voluntario", "instituicao", "curso" })
    List<Vinculo> findAll();

    @Override
    @EntityGraph(attributePaths = { "voluntario", "instituicao", "curso" })
    Optional<Vinculo> findById(Long id);

    @Override
    @EntityGraph(attributePaths = { "voluntario", "instituicao", "curso" })
    Page<Vinculo> findAll(Pageable pageable);

    // Busca todos os vínculos acadêmicos de um voluntário específico
    @EntityGraph(attributePaths = { "voluntario", "instituicao", "curso" })
    List<Vinculo> findByVoluntarioId(Long voluntarioId);

    // Filtra os vínculos por instituição (ex: listar todos os alunos da UFRN no
    // sistema)
    @EntityGraph(attributePaths = { "voluntario", "instituicao", "curso" })
    List<Vinculo> findByInstituicaoId(Long instituicaoId);
}