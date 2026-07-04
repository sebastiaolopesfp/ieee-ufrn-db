package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    
    // Busca os relatórios gerados por um diretor específico
    List<Relatorio> findByDiretorVoluntarioId(Long voluntarioId);
    
    // Busca relatórios emitidos por uma determinada Unidade Organizacional (Capítulo ou Ramo)
    List<Relatorio> findByUnidadeUnidadeCodigo(String unidadeCodigo);
}