package br.ufrn.ieee.database.organizacional.service;

import br.ufrn.ieee.database.organizacional.dto.CapituloRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.CapituloResponseDTO;
import br.ufrn.ieee.database.organizacional.model.Capitulo;
import br.ufrn.ieee.database.organizacional.model.RamoEstudantil;
import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;
import br.ufrn.ieee.database.organizacional.repository.CapituloRepository;
import br.ufrn.ieee.database.organizacional.repository.RamoEstudantilRepository;
import br.ufrn.ieee.database.organizacional.repository.UnidadeOrganizacionalRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CapituloService {

    private final CapituloRepository capituloRepository;
    private final UnidadeOrganizacionalRepository unidadeRepository;
    private final RamoEstudantilRepository ramoRepository;

    public CapituloService(CapituloRepository capituloRepository,
            UnidadeOrganizacionalRepository unidadeRepository,
            RamoEstudantilRepository ramoRepository) {
        this.capituloRepository = capituloRepository;
        this.unidadeRepository = unidadeRepository;
        this.ramoRepository = ramoRepository;
    }

    @Transactional(readOnly = true)
    public List<CapituloResponseDTO> listarTodos() {
        return capituloRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CapituloResponseDTO buscarPorId(String id) {
        Capitulo capitulo = buscarEntidadeOuFalhar(id);
        return toResponseDTO(capitulo);
    }

    @Transactional
    public CapituloResponseDTO criar(CapituloRequestDTO dto) {
        if (unidadeRepository.existsById(dto.getUnidadeCodigo())) {
            throw new RegraDeNegocioException("Já existe uma unidade com este código.");
        }

        RamoEstudantil ramo = ramoRepository.findById(dto.getRamoCodigo())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ramo Estudantil não encontrado."));

        UnidadeOrganizacional unidade = new UnidadeOrganizacional();
        unidade.setUnidadeCodigo(dto.getUnidadeCodigo());
        unidade.setNome(dto.getNome());
        unidade.setEmail(dto.getEmail());
        unidade.setAnoCriacao(dto.getAnoCriacao());
        unidade = unidadeRepository.save(unidade);

        Capitulo capitulo = new Capitulo();
        capitulo.setUnidade(unidade);
        capitulo.setRamo(ramo);

        Capitulo capituloSalvo = capituloRepository.save(capitulo);
        return toResponseDTO(capituloSalvo);
    }

    @Transactional
    public CapituloResponseDTO atualizar(String id, CapituloRequestDTO dto) {
        Capitulo capitulo = buscarEntidadeOuFalhar(id);
        UnidadeOrganizacional unidade = capitulo.getUnidade();

        if (dto.getNome() != null)
            unidade.setNome(dto.getNome());
        if (dto.getEmail() != null)
            unidade.setEmail(dto.getEmail());
        if (dto.getAnoCriacao() != null)
            unidade.setAnoCriacao(dto.getAnoCriacao());

        if (dto.getRamoCodigo() != null && !dto.getRamoCodigo().equals(capitulo.getRamo().getUnidadeCodigo())) {
            RamoEstudantil novoRamo = ramoRepository.findById(dto.getRamoCodigo())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Novo Ramo Estudantil não encontrado."));
            capitulo.setRamo(novoRamo);
        }

        unidadeRepository.save(unidade);
        Capitulo capituloAtualizado = capituloRepository.save(capitulo);

        return toResponseDTO(capituloAtualizado);
    }

    @Transactional
    public void deletar(String id) {
        Capitulo capitulo = buscarEntidadeOuFalhar(id);
        UnidadeOrganizacional unidade = capitulo.getUnidade();
        capituloRepository.delete(capitulo);
        unidadeRepository.delete(unidade);
    }

    private Capitulo buscarEntidadeOuFalhar(String id) {
        return capituloRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Capítulo não encontrado com Código: " + id));
    }

    private CapituloResponseDTO toResponseDTO(Capitulo capitulo) {
        CapituloResponseDTO dto = new CapituloResponseDTO();
        dto.setUnidadeCodigo(capitulo.getUnidadeCodigo());
        dto.setNome(capitulo.getUnidade().getNome());
        dto.setEmail(capitulo.getUnidade().getEmail());
        dto.setAnoCriacao(capitulo.getUnidade().getAnoCriacao());
        dto.setRamoCodigo(capitulo.getRamo().getUnidadeCodigo());
        dto.setNomeRamo(capitulo.getRamo().getUnidade().getNome());
        return dto;
    }
}