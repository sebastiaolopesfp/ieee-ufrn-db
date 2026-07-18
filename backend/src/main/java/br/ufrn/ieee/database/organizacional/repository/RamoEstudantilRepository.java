package br.ufrn.ieee.database.organizacional.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.organizacional.model.RamoEstudantil;

import java.util.Optional;

public interface RamoEstudantilRepository extends JpaRepository<RamoEstudantil, String> {

    @Override
    @EntityGraph(attributePaths = { "unidade" })
    Optional<RamoEstudantil> findById(String id);

    @Override
    @EntityGraph(attributePaths = { "unidade" })
    Page<RamoEstudantil> findAll(Pageable pageable);
}