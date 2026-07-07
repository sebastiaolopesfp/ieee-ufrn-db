package br.ufrn.ieee.database.evento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.evento.model.Evento;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    // Preparado para buscar o evento a partir do ID vindo da API externa do vTools
    Optional<Evento> findByVtoolsId(String vtoolsId);
    
    // Busca eventos por uma determinada categoria (ex: "Technical", "Professional")
    List<Evento> findByCategoria(String categoria);
}