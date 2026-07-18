package br.ufrn.ieee.database.organizacional.service;

import br.ufrn.ieee.database.organizacional.dto.RamoEstudantilRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.RamoEstudantilResponseDTO;
import br.ufrn.ieee.database.organizacional.model.RamoEstudantil;
import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;
import br.ufrn.ieee.database.organizacional.repository.RamoEstudantilRepository;
import br.ufrn.ieee.database.organizacional.repository.UnidadeOrganizacionalRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RamoEstudantilService {

    private final RamoEstudantilRepository ramoRepository;
    private final UnidadeOrganizacionalRepository unidadeRepository;

    public RamoEstudantilService(RamoEstudantilRepository ramoRepository,
            UnidadeOrganizacionalRepository unidadeRepository) {
        this.ramoRepository = ramoRepository;
        this.unidadeRepository = unidadeRepository;
    }

    @Transactional(readOnly = true)
    public Page<RamoEstudantilResponseDTO> listarTodos(Pageable pageable) {
        return ramoRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public RamoEstudantilResponseDTO buscarPorId(String id) {
        RamoEstudantil ramo = buscarEntidadeOuFalhar(id);
        return toResponseDTO(ramo);
    }

    @Transactional
    public RamoEstudantilResponseDTO criar(RamoEstudantilRequestDTO dto) {
        if (unidadeRepository.existsById(dto.getUnidadeCodigo())) {
            throw new RegraDeNegocioException("Já existe uma unidade com este código.");
        }

        UnidadeOrganizacional unidade = new UnidadeOrganizacional();
        unidade.setUnidadeCodigo(dto.getUnidadeCodigo());
        unidade.setNome(dto.getNome());
        unidade.setEmail(dto.getEmail());
        unidade.setAnoCriacao(dto.getAnoCriacao());
        unidade = unidadeRepository.save(unidade);

        RamoEstudantil ramo = new RamoEstudantil();
        ramo.setUnidade(unidade);
        RamoEstudantil ramoSalvo = ramoRepository.save(ramo);
        return toResponseDTO(ramoSalvo);
    }

    @Transactional
    public RamoEstudantilResponseDTO atualizar(String id, RamoEstudantilRequestDTO dto) {
        RamoEstudantil ramo = buscarEntidadeOuFalhar(id);
        UnidadeOrganizacional unidade = ramo.getUnidade();

        if (dto.getNome() != null)
            unidade.setNome(dto.getNome());
        if (dto.getEmail() != null)
            unidade.setEmail(dto.getEmail());
        if (dto.getAnoCriacao() != null)
            unidade.setAnoCriacao(dto.getAnoCriacao());

        unidadeRepository.save(unidade);
        return toResponseDTO(ramo);
    }

    @Transactional
    public void deletar(String id) {
        RamoEstudantil ramo = buscarEntidadeOuFalhar(id);
        UnidadeOrganizacional unidade = ramo.getUnidade();

        ramoRepository.delete(ramo);
        unidadeRepository.delete(unidade);
    }

    private RamoEstudantil buscarEntidadeOuFalhar(String id) {
        return ramoRepository.findById(id)
                .orElseThrow(
                        () -> new EntidadeNaoEncontradaException("Ramo Estudantil não encontrado com Código: " + id));
    }

    private RamoEstudantilResponseDTO toResponseDTO(RamoEstudantil ramo) {
        RamoEstudantilResponseDTO dto = new RamoEstudantilResponseDTO();
        dto.setUnidadeCodigo(ramo.getUnidadeCodigo());
        dto.setNome(ramo.getUnidade().getNome());
        dto.setEmail(ramo.getUnidade().getEmail());
        dto.setAnoCriacao(ramo.getUnidade().getAnoCriacao());
        return dto;
    }
}