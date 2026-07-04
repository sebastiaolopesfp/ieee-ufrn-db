package br.ufrn.ieee.database.voluntario.service;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.ieee.database.gestao.model.Cargo;
import br.ufrn.ieee.database.gestao.model.Mandato;
import br.ufrn.ieee.database.gestao.repository.CargoRepository;
import br.ufrn.ieee.database.gestao.repository.MandatoRepository;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import br.ufrn.ieee.database.voluntario.dto.PromoverDiretorRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.PromoverMembroRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioResponseDTO;
import br.ufrn.ieee.database.voluntario.model.Diretor;
import br.ufrn.ieee.database.voluntario.model.Membro;
import br.ufrn.ieee.database.voluntario.model.TipoUsuario;
import br.ufrn.ieee.database.voluntario.model.Voluntario;
import br.ufrn.ieee.database.voluntario.repository.DiretorRepository;
import br.ufrn.ieee.database.voluntario.repository.MembroRepository;
import br.ufrn.ieee.database.voluntario.repository.VoluntarioRepository;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final MembroRepository membroRepository;
    private final DiretorRepository diretorRepository;
    private final CargoRepository cargoRepository;
    private final MandatoRepository mandatoRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public VoluntarioService(
            VoluntarioRepository voluntarioRepository, 
            MembroRepository membroRepository,
            DiretorRepository diretorRepository,
            CargoRepository cargoRepository,
            MandatoRepository mandatoRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.voluntarioRepository = voluntarioRepository;
        this.membroRepository = membroRepository;
        this.diretorRepository = diretorRepository;
        this.cargoRepository = cargoRepository;
        this.mandatoRepository = mandatoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public VoluntarioResponseDTO cadastrarVoluntario(VoluntarioRequestDTO dto) {
        if (voluntarioRepository.findByEmailPessoal(dto.getEmailPessoal()).isPresent()) {
            throw new RegraDeNegocioException("E-mail já cadastrado no sistema.");
        }
        if (voluntarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RegraDeNegocioException("CPF já cadastrado no sistema.");
        }

        Voluntario voluntario = new Voluntario();
        voluntario.setPrimeiroNome(dto.getPrimeiroNome());
        voluntario.setNomeMeio(dto.getNomeMeio());
        voluntario.setUltimoNome(dto.getUltimoNome());
        voluntario.setEmailPessoal(dto.getEmailPessoal());
        voluntario.setTelefone(dto.getTelefone());
        voluntario.setCpf(dto.getCpf());
        
        voluntario.setSenha(passwordEncoder.encode(dto.getSenha()));
        
        voluntario.setTipoUsuario(TipoUsuario.VOLUNTARIO); 

        Voluntario voluntarioSalvo = voluntarioRepository.save(voluntario);

        VoluntarioResponseDTO resposta = new VoluntarioResponseDTO();
        resposta.setId(voluntarioSalvo.getId());
        resposta.setPrimeiroNome(voluntarioSalvo.getPrimeiroNome());
        resposta.setUltimoNome(voluntarioSalvo.getUltimoNome());
        resposta.setEmailPessoal(voluntarioSalvo.getEmailPessoal());
        resposta.setTipoUsuario(voluntarioSalvo.getTipoUsuario().name());

        return resposta;
    }

    @Transactional
    public void promoverAMembro(Long id, PromoverMembroRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (membroRepository.existsById(id)) {
            throw new RegraDeNegocioException("Este voluntário já é um Membro.");
        }

        voluntario.setTipoUsuario(TipoUsuario.MEMBRO);

        Membro membro = new Membro();
        membro.setVoluntario(voluntario);
        membro.setVoluntarioId(voluntario.getId());
        membro.setNumMembresia(dto.getNumeroMembresia());
        membro.setTipoMembresia(dto.getTipoMembresia());
        membro.setEmailIeee(dto.getEmailIeee());
        membro.setDataInicio(LocalDate.now());

        voluntario.setMembro(membro);

        voluntarioRepository.save(voluntario);
    }

    @Transactional
    public void promoverADiretor(Long id, PromoverDiretorRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));
        
        Membro membro = membroRepository.findById(id)
            .orElseThrow(() -> new RegraDeNegocioException("Apenas Membros ativos podem ser promovidos a Diretor."));

        if (diretorRepository.existsById(id)) {
            throw new RegraDeNegocioException("Este membro já é um Diretor.");
        }

        if (dto.getDataFim().isBefore(dto.getDataInicio())) {
            throw new RegraDeNegocioException("A data de término do mandato não pode ser anterior à data de início.");
        }

        if (dto.getTipoDiretor() != TipoUsuario.DIRETOR_RAMO && dto.getTipoDiretor() != TipoUsuario.DIRETOR_CAPITULO) {
            throw new RegraDeNegocioException("Tipo de diretor inválido para promoção.");
        }

        Cargo cargo = cargoRepository.findById(dto.getCargoId())
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Cargo informado para o mandato não existe."));

        voluntario.setTipoUsuario(dto.getTipoDiretor());
        voluntarioRepository.save(voluntario);

        Diretor diretor = new Diretor();
        diretor.setMembro(membro);
        diretor.setVoluntarioId(membro.getVoluntarioId());

        membro.setDiretor(diretor);

        membroRepository.save(membro);

        Mandato mandato = new Mandato();
        mandato.setDiretor(diretor);
        mandato.setCargo(cargo);
        mandato.setDataInicio(dto.getDataInicio());
        mandato.setDataFim(dto.getDataFim());

        mandatoRepository.save(mandato);
    }
}