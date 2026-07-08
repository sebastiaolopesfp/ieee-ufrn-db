package br.ufrn.ieee.database.evento.service;

import br.ufrn.ieee.database.evento.dto.SessaoRequestDTO;
import br.ufrn.ieee.database.evento.dto.SessaoResponseDTO;
import br.ufrn.ieee.database.evento.model.Evento;
import br.ufrn.ieee.database.evento.model.Sessao;
import br.ufrn.ieee.database.evento.repository.EventoRepository;
import br.ufrn.ieee.database.evento.repository.SessaoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import br.ufrn.ieee.database.voluntario.model.Voluntario;
import br.ufrn.ieee.database.voluntario.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SessaoService {

    private final SessaoRepository sessaoRepository;
    private final EventoRepository eventoRepository;
    private final VoluntarioRepository voluntarioRepository;

    public SessaoService(SessaoRepository sessaoRepository,
            EventoRepository eventoRepository,
            VoluntarioRepository voluntarioRepository) {
        this.sessaoRepository = sessaoRepository;
        this.eventoRepository = eventoRepository;
        this.voluntarioRepository = voluntarioRepository;
    }

    @Transactional(readOnly = true)
    public List<SessaoResponseDTO> listarPorEvento(Long eventoId) {
        return sessaoRepository.findByEventoIdOrderByDataAscHoraInicioAsc(eventoId).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SessaoResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadeOuFalhar(id));
    }

    @Transactional
    public SessaoResponseDTO criar(SessaoRequestDTO dto) {
        Evento evento = eventoRepository.findById(dto.getEventoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Evento não encontrado."));

        Sessao sessao = new Sessao();
        sessao.setEvento(evento);
        sessao.setTituloAtividade(dto.getTituloAtividade());
        sessao.setData(dto.getData());
        sessao.setHoraInicio(dto.getHoraInicio());
        sessao.setHoraFim(dto.getHoraFim());
        sessao.setLocal(dto.getLocal());

        return toResponseDTO(sessaoRepository.save(sessao));
    }

    @Transactional
    public SessaoResponseDTO atualizar(Long id, SessaoRequestDTO dto) {
        Sessao sessao = buscarEntidadeOuFalhar(id);

        if (dto.getTituloAtividade() != null)
            sessao.setTituloAtividade(dto.getTituloAtividade());
        if (dto.getData() != null)
            sessao.setData(dto.getData());
        if (dto.getHoraInicio() != null)
            sessao.setHoraInicio(dto.getHoraInicio());
        if (dto.getHoraFim() != null)
            sessao.setHoraFim(dto.getHoraFim());
        if (dto.getLocal() != null)
            sessao.setLocal(dto.getLocal());

        return toResponseDTO(sessaoRepository.save(sessao));
    }

    @Transactional
    public void deletar(Long id) {
        Sessao sessao = buscarEntidadeOuFalhar(id);
        sessaoRepository.delete(sessao);
    }

    @Transactional
    public SessaoResponseDTO registrarPresenca(Long sessaoId, Set<Long> voluntarioIds) {
        Sessao sessao = buscarEntidadeOuFalhar(sessaoId);

        List<Voluntario> voluntarios = voluntarioRepository.findAllById(voluntarioIds);
        if (voluntarios.isEmpty()) {
            throw new RegraDeNegocioException("Nenhum voluntário válido foi encontrado para registro.");
        }

        sessao.getVoluntarios().addAll(voluntarios);

        return toResponseDTO(sessaoRepository.save(sessao));
    }

    @Transactional
    public SessaoResponseDTO removerPresenca(Long sessaoId, Long voluntarioId) {
        Sessao sessao = buscarEntidadeOuFalhar(sessaoId);
        Voluntario voluntario = voluntarioRepository.findById(voluntarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        sessao.getVoluntarios().remove(voluntario);

        return toResponseDTO(sessaoRepository.save(sessao));
    }

    private Sessao buscarEntidadeOuFalhar(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Sessão não encontrada com ID: " + id));
    }

    private SessaoResponseDTO toResponseDTO(Sessao sessao) {
        SessaoResponseDTO dto = new SessaoResponseDTO();
        dto.setId(sessao.getId());
        dto.setEventoId(sessao.getEvento().getId());
        dto.setTituloAtividade(sessao.getTituloAtividade());
        dto.setData(sessao.getData());
        dto.setHoraInicio(sessao.getHoraInicio());
        dto.setHoraFim(sessao.getHoraFim());
        dto.setLocal(sessao.getLocal());

        Set<Long> idsPresentes = sessao.getVoluntarios().stream()
                .map(Voluntario::getId)
                .collect(Collectors.toSet());
        dto.setVoluntariosPresentesIds(idsPresentes);

        return dto;
    }
}