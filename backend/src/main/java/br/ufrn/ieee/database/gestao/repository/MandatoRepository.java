package br.ufrn.ieee.database.gestao.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.gestao.model.Mandato;

import java.util.List;

public interface MandatoRepository extends JpaRepository<Mandato, Long> {

    // Busca o histórico de mandatos de um diretor específico
    @EntityGraph(attributePaths = { "cargo", "diretor" })
    List<Mandato> findByDiretorVoluntarioId(Long voluntarioId);

    // Busca os mandatos ativos ou por cargo específico
    @EntityGraph(attributePaths = { "cargo", "diretor" })
    List<Mandato> findByCargoId(Long cargoId);
}