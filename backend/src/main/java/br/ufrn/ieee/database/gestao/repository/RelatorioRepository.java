package br.ufrn.ieee.database.gestao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.gestao.model.Relatorio;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    @Override
    @EntityGraph(attributePaths = { "diretor", "unidade" })
    Page<Relatorio> findAll(Pageable pageable);

    // Busca os relatórios gerados por um diretor específico
    @EntityGraph(attributePaths = { "diretor", "unidade" })
    List<Relatorio> findByDiretorVoluntarioId(Long voluntarioId);

    // Busca relatórios emitidos por uma determinada Unidade Organizacional
    // (Capítulo ou Ramo)
    @EntityGraph(attributePaths = { "diretor", "unidade" })
    List<Relatorio> findByUnidadeUnidadeCodigo(String unidadeCodigo);
}