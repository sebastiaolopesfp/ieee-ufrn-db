package br.ufrn.ieee.database.academico.service;

import br.ufrn.ieee.database.academico.dto.CursoResponseDTO;
import br.ufrn.ieee.database.academico.dto.InstituicaoRequestDTO;
import br.ufrn.ieee.database.academico.dto.InstituicaoResponseDTO;
import br.ufrn.ieee.database.academico.model.Curso;
import br.ufrn.ieee.database.academico.model.Instituicao;
import br.ufrn.ieee.database.academico.repository.CursoRepository;
import br.ufrn.ieee.database.academico.repository.InstituicaoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final CursoRepository cursoRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository, CursoRepository cursoRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<InstituicaoResponseDTO> listarTodos() {
        return instituicaoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public InstituicaoResponseDTO buscarPorId(Long id) {
        Instituicao instituicao = buscarEntidadeOuFalhar(id);
        return toResponseDTO(instituicao);
    }

    @Transactional
    public InstituicaoResponseDTO criar(InstituicaoRequestDTO dto) {
        Instituicao instituicao = new Instituicao();
        instituicao.setNome(dto.getNome());
        instituicao.setSigla(dto.getSigla());
        instituicao.setCursos(buscarCursosOuFalhar(dto.getCursoIds()));

        Instituicao instituicaoSalva = instituicaoRepository.save(instituicao);
        return toResponseDTO(instituicaoSalva);
    }

    @Transactional
    public InstituicaoResponseDTO atualizar(Long id, InstituicaoRequestDTO dto) {
        Instituicao instituicao = buscarEntidadeOuFalhar(id);
        instituicao.setNome(dto.getNome());
        instituicao.setSigla(dto.getSigla());
        instituicao.setCursos(buscarCursosOuFalhar(dto.getCursoIds()));

        Instituicao instituicaoAtualizada = instituicaoRepository.save(instituicao);
        return toResponseDTO(instituicaoAtualizada);
    }

    public void deletar(Long id) {
        Instituicao instituicao = buscarEntidadeOuFalhar(id);
        instituicaoRepository.delete(instituicao);
    }

    private Instituicao buscarEntidadeOuFalhar(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Instituição não encontrada com ID: " + id));
    }

    /**
     * Busca os Cursos JÁ CADASTRADOS pelos IDs recebidos no DTO.
     * Se algum ID não existir, a operação inteira falha — não criamos
     * Cursos "no ar" a partir do cadastro de uma Instituição.
     */
    private List<Curso> buscarCursosOuFalhar(List<Long> cursoIds) {
        if (cursoIds == null || cursoIds.isEmpty()) {
            return List.of();
        }

        List<Curso> cursosEncontrados = cursoRepository.findAllById(cursoIds);

        if (cursosEncontrados.size() != cursoIds.size()) {
            throw new EntidadeNaoEncontradaException("Um ou mais Cursos informados não foram encontrados.");
        }

        return cursosEncontrados;
    }

    private InstituicaoResponseDTO toResponseDTO(Instituicao instituicao) {
        InstituicaoResponseDTO dto = new InstituicaoResponseDTO();
        dto.setId(instituicao.getId());
        dto.setNome(instituicao.getNome());
        dto.setSigla(instituicao.getSigla());

        List<CursoResponseDTO> cursosDto = instituicao.getCursos() == null
                ? List.of()
                : instituicao.getCursos().stream()
                    .map(curso -> {
                        CursoResponseDTO cursoDto = new CursoResponseDTO();
                        cursoDto.setId(curso.getId());
                        cursoDto.setNome(curso.getNome());
                        return cursoDto;
                    })
                    .toList();

        dto.setCursos(cursosDto);
        return dto;
    }
}