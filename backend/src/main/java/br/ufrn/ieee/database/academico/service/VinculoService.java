package br.ufrn.ieee.database.academico.service;

import br.ufrn.ieee.database.academico.dto.VinculoRequestDTO;
import br.ufrn.ieee.database.academico.dto.VinculoResponseDTO;
import br.ufrn.ieee.database.academico.model.Curso;
import br.ufrn.ieee.database.academico.model.Instituicao;
import br.ufrn.ieee.database.academico.model.Vinculo;
import br.ufrn.ieee.database.academico.repository.CursoRepository;
import br.ufrn.ieee.database.academico.repository.InstituicaoRepository;
import br.ufrn.ieee.database.academico.repository.VinculoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.voluntario.model.Voluntario;
import br.ufrn.ieee.database.voluntario.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VinculoService {

    private final VinculoRepository vinculoRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final CursoRepository cursoRepository;

    public VinculoService(VinculoRepository vinculoRepository,
                           VoluntarioRepository voluntarioRepository,
                           InstituicaoRepository instituicaoRepository,
                           CursoRepository cursoRepository) {
        this.vinculoRepository = vinculoRepository;
        this.voluntarioRepository = voluntarioRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.cursoRepository = cursoRepository;
    }

    public List<VinculoResponseDTO> listarTodos() {
        return vinculoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public VinculoResponseDTO buscarPorId(Long id) {
        Vinculo vinculo = buscarEntidadeOuFalhar(id);
        return toResponseDTO(vinculo);
    }

    @Transactional
    public VinculoResponseDTO criar(VinculoRequestDTO dto) {
        Voluntario voluntario = buscarVoluntarioOuFalhar(dto.getVoluntarioId());
        Instituicao instituicao = buscarInstituicaoOuFalhar(dto.getInstituicaoId());
        Curso curso = buscarCursoOuFalhar(dto.getCursoId());

        Vinculo vinculo = new Vinculo();
        vinculo.setVoluntario(voluntario);
        vinculo.setInstituicao(instituicao);
        vinculo.setCurso(curso);
        vinculo.setNumMatricula(dto.getNumMatricula());
        vinculo.setEmailAcademico(dto.getEmailAcademico());
        vinculo.setAnoIngresso(dto.getAnoIngresso());
        vinculo.setStatusAcademico(dto.getStatusAcademico());

        Vinculo vinculoSalvo = vinculoRepository.save(vinculo);
        return toResponseDTO(vinculoSalvo);
    }

    @Transactional
    public VinculoResponseDTO atualizar(Long id, VinculoRequestDTO dto) {
        Vinculo vinculo = buscarEntidadeOuFalhar(id);

        // Revalida as FKs mesmo em atualização — o cliente pode estar tentando
        // trocar o vínculo para outra Instituição/Curso, por exemplo.
        Instituicao instituicao = buscarInstituicaoOuFalhar(dto.getInstituicaoId());
        Curso curso = buscarCursoOuFalhar(dto.getCursoId());

        vinculo.setInstituicao(instituicao);
        vinculo.setCurso(curso);
        vinculo.setNumMatricula(dto.getNumMatricula());
        vinculo.setEmailAcademico(dto.getEmailAcademico());
        vinculo.setAnoIngresso(dto.getAnoIngresso());
        vinculo.setStatusAcademico(dto.getStatusAcademico());

        Vinculo vinculoAtualizado = vinculoRepository.save(vinculo);
        return toResponseDTO(vinculoAtualizado);
    }

    public void deletar(Long id) {
        Vinculo vinculo = buscarEntidadeOuFalhar(id);
        vinculoRepository.delete(vinculo);
    }

    private Vinculo buscarEntidadeOuFalhar(Long id) {
        return vinculoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Vínculo não encontrado com ID: " + id));
    }

    private Voluntario buscarVoluntarioOuFalhar(Long id) {
        return voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado com ID: " + id));
    }

    private Instituicao buscarInstituicaoOuFalhar(Long id) {
        return instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Instituição não encontrada com ID: " + id));
    }

    private Curso buscarCursoOuFalhar(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Curso não encontrado com ID: " + id));
    }

    private VinculoResponseDTO toResponseDTO(Vinculo vinculo) {
        VinculoResponseDTO dto = new VinculoResponseDTO();
        dto.setId(vinculo.getId());
        dto.setVoluntarioId(vinculo.getVoluntario().getId());
        dto.setVoluntarioNomeCompleto(
                vinculo.getVoluntario().getPrimeiroNome() + " " + vinculo.getVoluntario().getUltimoNome());
        dto.setInstituicaoNome(vinculo.getInstituicao().getNome());
        dto.setCursoNome(vinculo.getCurso().getNome());
        dto.setNumMatricula(vinculo.getNumMatricula());
        dto.setEmailAcademico(vinculo.getEmailAcademico());
        dto.setAnoIngresso(vinculo.getAnoIngresso());
        dto.setStatusAcademico(vinculo.getStatusAcademico());
        return dto;
    }
}