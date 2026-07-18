package br.ufrn.ieee.database.evento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.evento.model.Evento;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Override
    @EntityGraph(attributePaths = { "unidades" })
    Optional<Evento> findById(Long id);

    // Mesmo trade-off já documentado no InstituicaoRepository: "unidades" é
    // uma coleção (@ManyToMany), então paginação + EntityGraph aqui pagina
    // em memória (aviso HHH000104 nos logs). Aceitável para o volume atual;
    // revisar com busca em duas etapas se a lista de eventos crescer muito.
    @Override
    @EntityGraph(attributePaths = { "unidades" })
    Page<Evento> findAll(Pageable pageable);

    // Preparado para buscar o evento a partir do ID vindo da API externa do vTools
    Optional<Evento> findByVtoolsId(String vtoolsId);

    // Busca eventos por uma determinada categoria (ex: "Technical", "Professional")
    @EntityGraph(attributePaths = { "unidades" })
    List<Evento> findByCategoria(String categoria);
}