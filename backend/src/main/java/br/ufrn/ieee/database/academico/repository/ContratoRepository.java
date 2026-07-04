package br.ufrn.ieee.database.academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.academico.model.Contrato;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    
    // Busca todos os contratos de um voluntário específico através do ID do vínculo dele
    List<Contrato> findByVinculoId(Long vinculoId);
    
    // Busca os contratos vinculados a uma Unidade Organizacional específica (Capítulo ou Ramo)
    List<Contrato> findByUnidadeUnidadeCodigo(String unidadeCodigo);
}