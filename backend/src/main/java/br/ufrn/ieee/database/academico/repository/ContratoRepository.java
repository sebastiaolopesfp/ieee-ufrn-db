package br.ufrn.ieee.database.academico.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.academico.model.Contrato;

import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    @Override
    @EntityGraph(attributePaths = { "vinculo", "vinculo.voluntario", "unidade" })
    List<Contrato> findAll();

    @Override
    @EntityGraph(attributePaths = { "vinculo", "vinculo.voluntario", "unidade" })
    Optional<Contrato> findById(Long id);

    @Override
    @EntityGraph(attributePaths = { "vinculo", "vinculo.voluntario", "unidade" })
    Page<Contrato> findAll(Pageable pageable);

    // Busca todos os contratos de um voluntário específico através do ID do vínculo
    // dele
    List<Contrato> findByVinculoId(Long vinculoId);

    // Busca os contratos vinculados a uma Unidade Organizacional específica
    // (Capítulo ou Ramo)
    @EntityGraph(attributePaths = { "vinculo", "vinculo.voluntario", "unidade" })
    List<Contrato> findByUnidadeUnidadeCodigo(String unidadeCodigo);
}