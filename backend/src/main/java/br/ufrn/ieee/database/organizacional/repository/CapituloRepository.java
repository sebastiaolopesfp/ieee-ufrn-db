package br.ufrn.ieee.database.organizacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.organizacional.model.Capitulo;

import java.util.List;

public interface CapituloRepository extends JpaRepository<Capitulo, String> {

    // Busca todos os capítulos vinculados a um determinado Ramo Estudantil
    List<Capitulo> findByRamoUnidadeCodigo(String ramoCodigo);
}