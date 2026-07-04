package br.ufrn.ieee.database.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.gestao.model.Mandato;

import java.util.List;

public interface MandatoRepository extends JpaRepository<Mandato, Long> {
    
    // Busca o histórico de mandatos de um diretor específico
    List<Mandato> findByDiretorVoluntarioId(Long voluntarioId);
    
    // Busca os mandatos ativos ou por cargo específico
    List<Mandato> findByCargoId(Long cargoId);
}