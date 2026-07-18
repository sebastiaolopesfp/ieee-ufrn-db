package br.ufrn.ieee.database.academico.service;

import br.ufrn.ieee.database.academico.dto.ContratoRequestDTO;
import br.ufrn.ieee.database.academico.dto.ContratoResponseDTO;
import br.ufrn.ieee.database.academico.model.Contrato;
import br.ufrn.ieee.database.academico.model.Vinculo;
import br.ufrn.ieee.database.academico.repository.ContratoRepository;
import br.ufrn.ieee.database.academico.repository.VinculoRepository;
import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;
import br.ufrn.ieee.database.organizacional.repository.UnidadeOrganizacionalRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final VinculoRepository vinculoRepository;
    private final UnidadeOrganizacionalRepository unidadeRepository;

    public ContratoService(ContratoRepository contratoRepository,
            VinculoRepository vinculoRepository,
            UnidadeOrganizacionalRepository unidadeRepository) {
        this.contratoRepository = contratoRepository;
        this.vinculoRepository = vinculoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    @Transactional(readOnly = true)
    public Page<ContratoResponseDTO> listarTodos(Pageable pageable) {
        return contratoRepository.findAll(pageable).map(this::toResponseDTO);
    }

    public ContratoResponseDTO buscarPorId(Long id) {
        Contrato contrato = buscarEntidadeOuFalhar(id);
        return toResponseDTO(contrato);
    }

    @Transactional
    public ContratoResponseDTO criar(ContratoRequestDTO dto) {
        Vinculo vinculo = buscarVinculoOuFalhar(dto.getVinculoId());
        UnidadeOrganizacional unidade = buscarUnidadeOuFalhar(dto.getUnidadeCodigo());

        validarDatas(dto);

        Contrato contrato = new Contrato();
        contrato.setVinculo(vinculo);
        contrato.setUnidade(unidade);
        contrato.setDataInicio(dto.getDataInicio());
        contrato.setDataFim(dto.getDataFim());
        contrato.setTermoCompromissoPdfPath(dto.getTermoCompromissoPdfPath());
        contrato.setTermoDesligamentoPdfPath(dto.getTermoDesligamentoPdfPath());

        Contrato contratoSalvo = contratoRepository.save(contrato);
        return toResponseDTO(contratoSalvo);
    }

    @Transactional
    public ContratoResponseDTO atualizar(Long id, ContratoRequestDTO dto) {
        Contrato contrato = buscarEntidadeOuFalhar(id);
        UnidadeOrganizacional unidade = buscarUnidadeOuFalhar(dto.getUnidadeCodigo());

        validarDatas(dto);

        contrato.setUnidade(unidade);
        contrato.setDataInicio(dto.getDataInicio());
        contrato.setDataFim(dto.getDataFim());
        contrato.setTermoCompromissoPdfPath(dto.getTermoCompromissoPdfPath());
        contrato.setTermoDesligamentoPdfPath(dto.getTermoDesligamentoPdfPath());

        Contrato contratoAtualizado = contratoRepository.save(contrato);
        return toResponseDTO(contratoAtualizado);
    }

    public void deletar(Long id) {
        Contrato contrato = buscarEntidadeOuFalhar(id);
        contratoRepository.delete(contrato);
    }

    private void validarDatas(ContratoRequestDTO dto) {
        if (dto.getDataFim().isBefore(dto.getDataInicio())) {
            throw new RegraDeNegocioException("A data de término do contrato não pode ser anterior à data de início.");
        }
    }

    private Contrato buscarEntidadeOuFalhar(Long id) {
        return contratoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Contrato não encontrado com ID: " + id));
    }

    private Vinculo buscarVinculoOuFalhar(Long id) {
        return vinculoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Vínculo não encontrado com ID: " + id));
    }

    private UnidadeOrganizacional buscarUnidadeOuFalhar(String codigo) {
        return unidadeRepository.findById(codigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Unidade Organizacional não encontrada com código: " + codigo));
    }

    private ContratoResponseDTO toResponseDTO(Contrato contrato) {
        ContratoResponseDTO dto = new ContratoResponseDTO();
        dto.setId(contrato.getId());
        dto.setVinculoId(contrato.getVinculo().getId());
        dto.setVoluntarioNomeCompleto(
                contrato.getVinculo().getVoluntario().getPrimeiroNome()
                        + " " + contrato.getVinculo().getVoluntario().getUltimoNome());
        dto.setUnidadeCodigo(contrato.getUnidade().getUnidadeCodigo());
        dto.setUnidadeNome(contrato.getUnidade().getNome());
        dto.setDataInicio(contrato.getDataInicio());
        dto.setDataFim(contrato.getDataFim());
        dto.setTermoCompromissoPdfPath(contrato.getTermoCompromissoPdfPath());
        dto.setTermoDesligamentoPdfPath(contrato.getTermoDesligamentoPdfPath());
        return dto;
    }
}