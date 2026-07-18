package br.ufrn.ieee.database.academico.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.ieee.database.academico.model.Instituicao;

import java.util.List;
import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    @Override
    @EntityGraph(attributePaths = { "cursos" })
    List<Instituicao> findAll();

    @Override
    @EntityGraph(attributePaths = { "cursos" })
    Optional<Instituicao> findById(Long id);

    // ATENÇÃO (trade-off consciente): combinar @EntityGraph de uma COLEÇÃO
    // (cursos é @ManyToMany) com paginação faz o Hibernate logar o aviso
    // HHH000104 e paginar em memória (traz tudo, corta em Java). Para uma
    // tabela de catálogo pequena (instituições parceiras) isso é aceitável.
    // Se esse catálogo crescer muito, troque por busca em duas etapas:
    // pagina os IDs primeiro, depois busca com o grafo completo só pra
    // esses IDs.
    @Override
    @EntityGraph(attributePaths = { "cursos" })
    Page<Instituicao> findAll(Pageable pageable);

    // Busca a instituição pela sigla (ex: "UFRN")
    Optional<Instituicao> findBySiglaIgnoreCase(String sigla);
}