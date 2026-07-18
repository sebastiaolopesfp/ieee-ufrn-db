package br.ufrn.ieee.database.organizacional.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.organizacional.model.Capitulo;

import java.util.List;
import java.util.Optional;

public interface CapituloRepository extends JpaRepository<Capitulo, String> {

    @Override
    @EntityGraph(attributePaths = { "unidade", "ramo", "ramo.unidade" })
    Optional<Capitulo> findById(String id);

    @Override
    @EntityGraph(attributePaths = { "unidade", "ramo", "ramo.unidade" })
    Page<Capitulo> findAll(Pageable pageable);

    // Busca todos os capítulos vinculados a um determinado Ramo Estudantil
    @EntityGraph(attributePaths = { "unidade", "ramo", "ramo.unidade" })
    List<Capitulo> findByRamoUnidadeCodigo(String ramoCodigo);
}