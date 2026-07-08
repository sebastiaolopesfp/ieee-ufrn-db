package br.ufrn.ieee.database.organizacional.service;

import br.ufrn.ieee.database.organizacional.dto.GrupoDeAfinidadeRequestDTO;
import br.ufrn.ieee.database.organizacional.dto.GrupoDeAfinidadeResponseDTO;
import br.ufrn.ieee.database.organizacional.model.GrupoDeAfinidade;
import br.ufrn.ieee.database.organizacional.model.RamoEstudantil;
import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;
import br.ufrn.ieee.database.organizacional.repository.GrupoDeAfinidadeRepository;
import br.ufrn.ieee.database.organizacional.repository.RamoEstudantilRepository;
import br.ufrn.ieee.database.organizacional.repository.UnidadeOrganizacionalRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoDeAfinidadeService {

    private final GrupoDeAfinidadeRepository grupoRepository;
    private final UnidadeOrganizacionalRepository unidadeRepository;
    private final RamoEstudantilRepository ramoRepository;

    public GrupoDeAfinidadeService(GrupoDeAfinidadeRepository grupoRepository,
            UnidadeOrganizacionalRepository unidadeRepository,
            RamoEstudantilRepository ramoRepository) {
        this.grupoRepository = grupoRepository;
        this.unidadeRepository = unidadeRepository;
        this.ramoRepository = ramoRepository;
    }

    @Transactional(readOnly = true)
    public List<GrupoDeAfinidadeResponseDTO> listarTodos() {
        return grupoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public GrupoDeAfinidadeResponseDTO buscarPorId(String id) {
        GrupoDeAfinidade grupo = buscarEntidadeOuFalhar(id);
        return toResponseDTO(grupo);
    }

    @Transactional
    public GrupoDeAfinidadeResponseDTO criar(GrupoDeAfinidadeRequestDTO dto) {
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

        GrupoDeAfinidade grupo = new GrupoDeAfinidade();
        grupo.setUnidade(unidade);
        grupo.setRamo(ramo);

        GrupoDeAfinidade grupoSalvo = grupoRepository.save(grupo);
        return toResponseDTO(grupoSalvo);
    }

    @Transactional
    public GrupoDeAfinidadeResponseDTO atualizar(String id, GrupoDeAfinidadeRequestDTO dto) {
        GrupoDeAfinidade grupo = buscarEntidadeOuFalhar(id);
        UnidadeOrganizacional unidade = grupo.getUnidade();

        if (dto.getNome() != null)
            unidade.setNome(dto.getNome());
        if (dto.getEmail() != null)
            unidade.setEmail(dto.getEmail());
        if (dto.getAnoCriacao() != null)
            unidade.setAnoCriacao(dto.getAnoCriacao());

        if (dto.getRamoCodigo() != null && !dto.getRamoCodigo().equals(grupo.getRamo().getUnidadeCodigo())) {
            RamoEstudantil novoRamo = ramoRepository.findById(dto.getRamoCodigo())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Novo Ramo Estudantil não encontrado."));
            grupo.setRamo(novoRamo);
        }

        unidadeRepository.save(unidade);
        GrupoDeAfinidade grupoAtualizado = grupoRepository.save(grupo);
        return toResponseDTO(grupoAtualizado);
    }

    @Transactional
    public void deletar(String id) {
        GrupoDeAfinidade grupo = buscarEntidadeOuFalhar(id);
        UnidadeOrganizacional unidade = grupo.getUnidade();

        grupoRepository.delete(grupo);
        unidadeRepository.delete(unidade);
    }

    private GrupoDeAfinidade buscarEntidadeOuFalhar(String id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Grupo de Afinidade não encontrado com Código: " + id));
    }

    private GrupoDeAfinidadeResponseDTO toResponseDTO(GrupoDeAfinidade grupo) {
        GrupoDeAfinidadeResponseDTO dto = new GrupoDeAfinidadeResponseDTO();
        dto.setUnidadeCodigo(grupo.getUnidadeCodigo());
        dto.setNome(grupo.getUnidade().getNome());
        dto.setEmail(grupo.getUnidade().getEmail());
        dto.setAnoCriacao(grupo.getUnidade().getAnoCriacao());
        dto.setRamoCodigo(grupo.getRamo().getUnidadeCodigo());
        dto.setNomeRamo(grupo.getRamo().getUnidade().getNome());
        return dto;
    }
}