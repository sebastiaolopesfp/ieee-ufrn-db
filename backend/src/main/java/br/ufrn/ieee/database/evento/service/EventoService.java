package br.ufrn.ieee.database.evento.service;

import br.ufrn.ieee.database.evento.dto.EventoRequestDTO;
import br.ufrn.ieee.database.evento.dto.EventoResponseDTO;
import br.ufrn.ieee.database.evento.model.Evento;
import br.ufrn.ieee.database.evento.model.StatusSincronizacao;
import br.ufrn.ieee.database.evento.model.LocationType;
import br.ufrn.ieee.database.evento.repository.EventoRepository;
import br.ufrn.ieee.database.evento.utils.VToolsCategoryMapper;
import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;
import br.ufrn.ieee.database.organizacional.repository.UnidadeOrganizacionalRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UnidadeOrganizacionalRepository unidadeRepository;
    private final VToolsClientService vToolsClientService;
    private final VToolsCategoryMapper categoryMapper;

    public EventoService(EventoRepository eventoRepository,
            UnidadeOrganizacionalRepository unidadeRepository,
            VToolsClientService vToolsClientService,
            VToolsCategoryMapper categoryMapper) {
        this.eventoRepository = eventoRepository;
        this.unidadeRepository = unidadeRepository;
        this.vToolsClientService = vToolsClientService;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<EventoResponseDTO> listarTodos() {
        return eventoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public EventoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadeOuFalhar(id));
    }

    @Transactional
    public EventoResponseDTO criarLocalmente(EventoRequestDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataInicio(dto.getDataInicio());
        evento.setDataFim(dto.getDataFim());
        evento.setLocationType(dto.getLocationType() != null ? dto.getLocationType() : LocationType.UNKNOWN);
        evento.setCategoria(dto.getCategoria());
        evento.setSubcategoria(dto.getSubcategoria());

        if (dto.getOrcamentoEstimado() != null)
            evento.setOrcamentoEstimado(dto.getOrcamentoEstimado());

        if (dto.getVtoolsId() != null && !dto.getVtoolsId().isBlank()) {
            evento.setVtoolsId(dto.getVtoolsId());
            evento.setStatusSincronizacao(StatusSincronizacao.PENDENTE_ATUALIZACAO);
        } else {
            evento.setStatusSincronizacao(StatusSincronizacao.LOCAL_APENAS);
        }

        vincularUnidades(evento, dto.getUnidadesCodigos());

        return toResponseDTO(eventoRepository.save(evento));
    }

    @Transactional
    public EventoResponseDTO importarOuAtualizarDoVTools(String vtoolsId, String unidadeCodigoBase) {
        var vtoolsResponse = vToolsClientService.buscarEventoPorId(vtoolsId);
        var vtoolsAttr = vtoolsResponse.getAttributes();
        var vtoolsRels = vtoolsResponse.getRelationships();

        Optional<Evento> eventoExistente = eventoRepository.findByVtoolsId(vtoolsId);
        Evento evento = eventoExistente.orElseGet(Evento::new);

        evento.setVtoolsId(vtoolsAttr.getId());
        evento.setTitulo(vtoolsAttr.getTitle());
        evento.setDescricao(vtoolsAttr.getDescription());
        evento.setDataInicio(vtoolsAttr.getStartTime());
        evento.setDataFim(vtoolsAttr.getEndTime());

        evento.setPublished(vtoolsAttr.getPublish() != null ? vtoolsAttr.getPublish() : false);

        boolean isReported = (vtoolsAttr.getIeeeAttending() != null || vtoolsAttr.getGuestsAttending() != null);
        evento.setReported(isReported);

        evento.setQtdMembros(vtoolsAttr.getIeeeAttending() != null ? vtoolsAttr.getIeeeAttending() : 0);
        evento.setQtdNaoMembros(vtoolsAttr.getGuestsAttending() != null ? vtoolsAttr.getGuestsAttending() : 0);

        if (vtoolsAttr.getLocationType() != null) {
            evento.setLocationType(converterLocationType(vtoolsAttr.getLocationType()));
        } else if (vtoolsAttr.getVirtual() != null) {
            evento.setLocationType(vtoolsAttr.getVirtual() ? LocationType.VIRTUAL : LocationType.PHYSICAL);
        } else {
            evento.setLocationType(LocationType.UNKNOWN);
        }

        if (vtoolsRels != null && vtoolsRels.getCategory() != null && vtoolsRels.getCategory().getData() != null) {
            evento.setCategoria(categoryMapper.traduzirCategoria(vtoolsRels.getCategory().getData().getId()));
        }
        if (vtoolsRels != null && vtoolsRels.getSubcategory() != null
                && vtoolsRels.getSubcategory().getData() != null) {
            evento.setSubcategoria(categoryMapper.traduzirSubcategoria(vtoolsRels.getSubcategory().getData().getId()));
        }

        if (eventoExistente.isEmpty()) {
            if (unidadeCodigoBase != null) {
                UnidadeOrganizacional unidade = unidadeRepository.findById(unidadeCodigoBase)
                        .orElseThrow(() -> new EntidadeNaoEncontradaException("Unidade Base não encontrada."));
                evento.getUnidades().add(unidade);
            }
        }

        evento.setStatusSincronizacao(StatusSincronizacao.SINCRONIZADO);
        evento.setDataUltimaSincronizacao(Instant.now());

        return toResponseDTO(eventoRepository.save(evento));
    }

    @Transactional
    public EventoResponseDTO atualizarLocalmente(Long id, EventoRequestDTO dto) {
        Evento evento = buscarEntidadeOuFalhar(id);

        if (evento.getStatusSincronizacao() != StatusSincronizacao.LOCAL_APENAS) {
            throw new RegraDeNegocioException(
                    "Eventos importados do vTools devem ser alterados na plataforma oficial.");
        }

        // Atualiza os dados permitidos
        if (dto.getTitulo() != null)
            evento.setTitulo(dto.getTitulo());
        if (dto.getDescricao() != null)
            evento.setDescricao(dto.getDescricao());
        if (dto.getDataInicio() != null)
            evento.setDataInicio(dto.getDataInicio());
        if (dto.getDataFim() != null)
            evento.setDataFim(dto.getDataFim());
        if (dto.getLocationType() != null)
            evento.setLocationType(dto.getLocationType());
        if (dto.getCategoria() != null)
            evento.setCategoria(dto.getCategoria());
        if (dto.getSubcategoria() != null)
            evento.setSubcategoria(dto.getSubcategoria());
        if (dto.getOrcamentoEstimado() != null)
            evento.setOrcamentoEstimado(dto.getOrcamentoEstimado());

        // 👉 ADICIONE ESTAS DUAS LINHAS AQUI:
        if (dto.getQtdMembros() != null)
            evento.setQtdMembros(dto.getQtdMembros());
        if (dto.getQtdNaoMembros() != null)
            evento.setQtdNaoMembros(dto.getQtdNaoMembros());

        // Atualiza os vínculos organizacionais se foram enviados
        if (dto.getUnidadesCodigos() != null) {
            evento.getUnidades().clear();
            vincularUnidades(evento, dto.getUnidadesCodigos());
        }

        return toResponseDTO(eventoRepository.save(evento));
    }

    @Transactional
    public void deletar(Long id) {
        Evento evento = buscarEntidadeOuFalhar(id);
        eventoRepository.delete(evento);
    }

    private Evento buscarEntidadeOuFalhar(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado com ID: " + id));
    }

    private void vincularUnidades(Evento evento, Set<String> unidadesCodigos) {
        if (unidadesCodigos != null && !unidadesCodigos.isEmpty()) {
            Set<UnidadeOrganizacional> unidades = new HashSet<>(unidadeRepository.findAllById(unidadesCodigos));
            if (unidades.size() != unidadesCodigos.size()) {
                throw new EntidadeNaoEncontradaException(
                        "Uma ou mais Unidades Organizacionais informadas não existem.");
            }
            evento.setUnidades(unidades);
        }
    }

    private LocationType converterLocationType(String vtoolsLocation) {
        if (vtoolsLocation == null)
            return LocationType.UNKNOWN;
        return switch (vtoolsLocation.toLowerCase()) {
            case "virtual" -> LocationType.VIRTUAL;
            case "physical" -> LocationType.PHYSICAL;
            case "hybrid" -> LocationType.HYBRID;
            default -> LocationType.UNKNOWN;
        };
    }

    private EventoResponseDTO toResponseDTO(Evento evento) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescricao(evento.getDescricao());
        dto.setVtoolsId(evento.getVtoolsId());
        dto.setDataInicio(evento.getDataInicio());
        dto.setDataFim(evento.getDataFim());
        dto.setLocationType(evento.getLocationType());
        dto.setPublished(evento.getPublished());
        dto.setReported(evento.getReported());
        dto.setCategoria(evento.getCategoria());
        dto.setSubcategoria(evento.getSubcategoria());
        dto.setQtdMembros(evento.getQtdMembros());
        dto.setQtdNaoMembros(evento.getQtdNaoMembros());
        dto.setOrcamentoEstimado(evento.getOrcamentoEstimado());
        dto.setStatusSincronizacao(evento.getStatusSincronizacao());
        dto.setDataUltimaSincronizacao(evento.getDataUltimaSincronizacao());

        Set<String> codigos = evento.getUnidades().stream()
                .map(UnidadeOrganizacional::getUnidadeCodigo)
                .collect(Collectors.toSet());
        dto.setUnidadesCodigos(codigos);

        return dto;
    }

    public Map<String, String> listarCategorias() {
        return categoryMapper.getCategorias();
    }

    public Map<String, String> listarSubcategorias() {
        return categoryMapper.getSubcategorias();
    }
}