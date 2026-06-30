package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CapituloRepository extends JpaRepository<Capitulo, String> {

    // Busca todos os capítulos vinculados a um determinado Ramo Estudantil
    List<Capitulo> findByRamoUnidadeCodigo(String ramoCodigo);
}