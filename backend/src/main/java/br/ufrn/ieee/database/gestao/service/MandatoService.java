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
        List<Mandato> mandatos = mandatoRepository.findByDiretorVoluntarioId(diretorId);
        LocalDate hoje = LocalDate.now();

        for (Mandato mandato : mandatos) {
            boolean estariaAtivoOuFuturo = !hoje.isAfter(mandato.getDataFim());
            if (estariaAtivoOuFuturo) {
                mandato.setDataFim(hoje);
                mandatoRepository.save(mandato);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<MandatoResponseDTO> obterHistoricoMandatos(Long diretorId, boolean isDiretorAtivo) {
        List<Mandato> mandatos = mandatoRepository.findByDiretorVoluntarioId(diretorId);
        LocalDate hoje = LocalDate.now();

        return mandatos.stream()
                .map(mandato -> {
                    boolean ativo = isDiretorAtivo && !hoje.isBefore(mandato.getDataInicio())
                            && !hoje.isAfter(mandato.getDataFim());
                    return new MandatoResponseDTO(
                            mandato.getId(),
                            mandato.getCargo().getId(),
                            mandato.getCargo().getNome(),
                            mandato.getDataInicio(),
                            mandato.getDataFim(),
                            ativo);
                }).toList();
    }
}