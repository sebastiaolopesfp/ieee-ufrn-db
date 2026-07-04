package br.ufrn.ieee.database.organizacional.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.organizacional.model.Sessao;

import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    
    // Retorna a programação/sessões de um evento específico ordenada por data e hora
    List<Sessao> findByEventoIdOrderByDataAscHoraInicioAsc(Long eventoId);
}