package br.ufrn.ieee.database.organizacional.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.organizacional.model.GrupoDeAfinidade;

import java.util.List;
import java.util.Optional;

public interface GrupoDeAfinidadeRepository extends JpaRepository<GrupoDeAfinidade, String> {

    @Override
    @EntityGraph(attributePaths = { "unidade", "ramo", "ramo.unidade" })
    Optional<GrupoDeAfinidade> findById(String id);

    @Override
    @EntityGraph(attributePaths = { "unidade", "ramo", "ramo.unidade" })
    Page<GrupoDeAfinidade> findAll(Pageable pageable);

    // Lista os grupos de afinidade vinculados a um Ramo Estudantil específico
    @EntityGraph(attributePaths = { "unidade", "ramo", "ramo.unidade" })
    List<GrupoDeAfinidade> findByRamoUnidadeCodigo(String ramoCodigo);
}