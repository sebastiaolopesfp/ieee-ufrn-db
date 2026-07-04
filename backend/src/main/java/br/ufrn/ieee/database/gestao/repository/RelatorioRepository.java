package br.ufrn.ieee.database.gestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.gestao.model.Relatorio;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    
    // Busca os relatórios gerados por um diretor específico
    List<Relatorio> findByDiretorVoluntarioId(Long voluntarioId);
    
    // Busca relatórios emitidos por uma determinada Unidade Organizacional (Capítulo ou Ramo)
    List<Relatorio> findByUnidadeUnidadeCodigo(String unidadeCodigo);
}