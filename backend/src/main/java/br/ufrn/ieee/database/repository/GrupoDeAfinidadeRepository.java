package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.GrupoDeAfinidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrupoDeAfinidadeRepository extends JpaRepository<GrupoDeAfinidade, String> {
    
    // Lista os grupos de afinidade vinculados a um Ramo Estudantil específico
    List<GrupoDeAfinidade> findByRamoUnidadeCodigo(String ramoCodigo);
}