package br.ufrn.ieee.database.gestao.service;

import br.ufrn.ieee.database.gestao.dto.MandatoResponseDTO;
import br.ufrn.ieee.database.gestao.model.Cargo;
import br.ufrn.ieee.database.gestao.model.Mandato;
import br.ufrn.ieee.database.gestao.repository.CargoRepository;
import br.ufrn.ieee.database.gestao.repository.MandatoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.voluntario.model.Diretor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MandatoService {

    private final MandatoRepository mandatoRepository;
    private final CargoRepository cargoRepository;

    public MandatoService(MandatoRepository mandatoRepository, CargoRepository cargoRepository) {
        this.mandatoRepository = mandatoRepository;
        this.cargoRepository = cargoRepository;
    }

    @Transactional
    public void criarMandato(Diretor diretor, Long cargoId, LocalDate inicio, LocalDate fim) {
        Cargo cargo = cargoRepository.findById(cargoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo informado não existe."));

        Mandato mandato = new Mandato();
        mandato.setDiretor(diretor);
        mandato.setCargo(cargo);
        mandato.setDataInicio(inicio);
        mandato.setDataFim(fim);
        mandatoRepository.save(mandato);
    }

    @Transactional
    public void encerrarMandatosAtivosOuFuturos(Long diretorId) {
        List<Mandato> mandatos = mandatoRepository.findAll();
        LocalDate hoje = LocalDate.now();

        for (Mandato m : mandatos) {
            if (m.getDiretor().getVoluntarioId().equals(diretorId)) {
                boolean estariaAtivoOuFuturo = !hoje.isAfter(m.getDataFim());
                if (estariaAtivoOuFuturo) {
                    m.setDataFim(hoje);
                    mandatoRepository.save(m);
                }
            }
        }
    }

    @Transactional(readOnly = true)
    public List<MandatoResponseDTO> obterHistoricoMandatos(Long diretorId, boolean isDiretorAtivo) {
        List<Mandato> mandatos = mandatoRepository.findAll();
        LocalDate hoje = LocalDate.now();

        return mandatos.stream()
                .filter(m -> m.getDiretor().getVoluntarioId().equals(diretorId))
                .map(m -> {
                    boolean ativo = isDiretorAtivo && !hoje.isBefore(m.getDataInicio())
                            && !hoje.isAfter(m.getDataFim());
                    return new MandatoResponseDTO(
                            m.getId(),
                            m.getCargo().getId(),
                            m.getCargo().getNome(),
                            m.getDataInicio(),
                            m.getDataFim(),
                            ativo);
                }).toList();
    }
}