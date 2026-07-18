package br.ufrn.ieee.database.academico.service;

import br.ufrn.ieee.database.academico.dto.CursoRequestDTO;
import br.ufrn.ieee.database.academico.dto.CursoResponseDTO;
import br.ufrn.ieee.database.academico.model.Curso;
import br.ufrn.ieee.database.academico.repository.CursoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Page<CursoResponseDTO> listarTodos(Pageable pageable) {
        return cursoRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = buscarEntidadeOuFalhar(id);
        return toResponseDTO(curso);
    }

    public CursoResponseDTO criar(CursoRequestDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.getNome());

        Curso cursoSalvo = cursoRepository.save(curso);
        return toResponseDTO(cursoSalvo);
    }

    public CursoResponseDTO atualizar(Long id, CursoRequestDTO dto) {
        Curso curso = buscarEntidadeOuFalhar(id);
        curso.setNome(dto.getNome());

        Curso cursoAtualizado = cursoRepository.save(curso);
        return toResponseDTO(cursoAtualizado);
    }

    public void deletar(Long id) {
        Curso curso = buscarEntidadeOuFalhar(id);
        cursoRepository.delete(curso);
    }

    private Curso buscarEntidadeOuFalhar(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Curso não encontrado com ID: " + id));
    }

    private CursoResponseDTO toResponseDTO(Curso curso) {
        CursoResponseDTO dto = new CursoResponseDTO();
        dto.setId(curso.getId());
        dto.setNome(curso.getNome());
        return dto;
    }
}