package br.ufrn.ieee.database.gestao.service;

import br.ufrn.ieee.database.gestao.dto.RelatorioRequestDTO;
import br.ufrn.ieee.database.gestao.dto.RelatorioResponseDTO;
import br.ufrn.ieee.database.gestao.model.Relatorio;
import br.ufrn.ieee.database.gestao.repository.RelatorioRepository;
import br.ufrn.ieee.database.organizacional.model.UnidadeOrganizacional;
import br.ufrn.ieee.database.organizacional.repository.UnidadeOrganizacionalRepository;
import br.ufrn.ieee.database.voluntario.model.Diretor;
import br.ufrn.ieee.database.voluntario.repository.DiretorRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository relatorioRepository;
    private final DiretorRepository diretorRepository;
    private final UnidadeOrganizacionalRepository unidadeRepository;

    public RelatorioService(RelatorioRepository relatorioRepository, DiretorRepository diretorRepository,
            UnidadeOrganizacionalRepository unidadeRepository) {
        this.relatorioRepository = relatorioRepository;
        this.diretorRepository = diretorRepository;
        this.unidadeRepository = unidadeRepository;
    }

    public RelatorioResponseDTO criar(RelatorioRequestDTO dto) {
        Diretor diretor = diretorRepository.findById(dto.getDiretorId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Diretor não encontrado."));

        UnidadeOrganizacional unidade = unidadeRepository.findById(dto.getUnidadeCodigo())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Unidade Organizacional não encontrada."));

        Relatorio relatorio = new Relatorio();
        relatorio.setDiretor(diretor);
        relatorio.setUnidade(unidade);
        relatorio.setTipoRelatorio(dto.getTipoRelatorio());
        relatorio.setDataGeracao(LocalDateTime.now());
        relatorio.setDataInicioRelatorio(dto.getDataInicioRelatorio());
        relatorio.setDataFimRelatorio(dto.getDataFimRelatorio());
        relatorio.setRelatorioPdfPath(dto.getRelatorioPdfPath());

        return toResponseDTO(relatorioRepository.save(relatorio));
    }

    public List<RelatorioResponseDTO> listarTodos() {
        return relatorioRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    private RelatorioResponseDTO toResponseDTO(Relatorio relatorio) {
        RelatorioResponseDTO dto = new RelatorioResponseDTO();
        dto.setId(relatorio.getId());
        dto.setDiretorId(relatorio.getDiretor().getVoluntarioId());
        dto.setUnidadeCodigo(relatorio.getUnidade().getUnidadeCodigo());
        dto.setTipoRelatorio(relatorio.getTipoRelatorio());
        dto.setDataGeracao(relatorio.getDataGeracao());
        dto.setDataInicioRelatorio(relatorio.getDataInicioRelatorio());
        dto.setDataFimRelatorio(relatorio.getDataFimRelatorio());
        dto.setRelatorioPdfPath(relatorio.getRelatorioPdfPath());
        return dto;
    }
}