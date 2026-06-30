package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    
    // Busca todos os contratos de um voluntário específico através do ID do vínculo dele
    List<Contrato> findByVinculoId(Long vinculoId);
    
    // Busca os contratos vinculados a uma Unidade Organizacional específica (Capítulo ou Ramo)
    List<Contrato> findByUnidadeCodigo(String unidadeCodigo);
}