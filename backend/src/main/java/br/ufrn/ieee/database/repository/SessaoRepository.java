package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    
    // Retorna a programação/sessões de um evento específico ordenada por data e hora
    List<Sessao> findByEventoIdOrderByDataAscHoraInicioAsc(Long eventoId);
}