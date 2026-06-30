package br.ufrn.ieee.database.repository;

import br.ufrn.ieee.database.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
    
    // Busca a instituição pela sigla (ex: "UFRN")
    Optional<Instituicao> findBySiglaIgnoreCase(String sigla);
}