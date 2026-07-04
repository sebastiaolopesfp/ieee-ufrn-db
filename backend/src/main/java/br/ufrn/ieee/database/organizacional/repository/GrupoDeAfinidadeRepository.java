package br.ufrn.ieee.database.organizacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.organizacional.model.GrupoDeAfinidade;

import java.util.List;

public interface GrupoDeAfinidadeRepository extends JpaRepository<GrupoDeAfinidade, String> {
    
    // Lista os grupos de afinidade vinculados a um Ramo Estudantil específico
    List<GrupoDeAfinidade> findByRamoUnidadeCodigo(String ramoCodigo);
}