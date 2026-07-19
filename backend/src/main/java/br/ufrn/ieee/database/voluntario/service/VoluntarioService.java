package br.ufrn.ieee.database.voluntario.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufrn.ieee.database.gestao.dto.MandatoResponseDTO;
import br.ufrn.ieee.database.gestao.service.MandatoService;
import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import br.ufrn.ieee.database.voluntario.dto.*;
import br.ufrn.ieee.database.voluntario.model.*;
import br.ufrn.ieee.database.voluntario.repository.*;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final MembroRepository membroRepository;
    private final DiretorRepository diretorRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MandatoService mandatoService;

    public VoluntarioService(
            VoluntarioRepository voluntarioRepository,
            MembroRepository membroRepository,
            DiretorRepository diretorRepository,
            BCryptPasswordEncoder passwordEncoder,
            MandatoService mandatoService) {
        this.voluntarioRepository = voluntarioRepository;
        this.membroRepository = membroRepository;
        this.diretorRepository = diretorRepository;
        this.passwordEncoder = passwordEncoder;
        this.mandatoService = mandatoService;
    }

    @Transactional(readOnly = true)
    public Page<VoluntarioResponseDTO> listarTodos(Pageable pageable, boolean incluirInativos) {
        Page<Voluntario> pagina = incluirInativos
                ? voluntarioRepository.findAll(pageable)
                : voluntarioRepository.findByAtivoTrue(pageable);
        return pagina.map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public VoluntarioResponseDTO buscarPorId(Long id) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));
        return toResponseDTO(voluntario);
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
        return toResponseDTO(voluntarioSalvo);
    }

    @Transactional
    public VoluntarioResponseDTO atualizar(Long id, VoluntarioAtualizacaoRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        voluntario.setPrimeiroNome(dto.getPrimeiroNome());
        voluntario.setNomeMeio(dto.getNomeMeio());
        voluntario.setUltimoNome(dto.getUltimoNome());
        voluntario.setTelefone(dto.getTelefone());

        Voluntario voluntarioSalvo = voluntarioRepository.save(voluntario);
        return toResponseDTO(voluntarioSalvo);
    }

    @Transactional
    public void deletar(Long id) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        // Garante a integridade referencial: históricos de membresia são preservados e
        // não podem ser apagados
        if (membroRepository.existsById(id)) {
            throw new RegraDeNegocioException(
                    "Não é possível excluir um voluntário com histórico de membresia ativo ou encerrado.");
        }

        voluntarioRepository.delete(voluntario);
    }

    @Transactional
    public void desativar(Long id) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (!voluntario.getAtivo()) {
            throw new RegraDeNegocioException("Este voluntário já está inativo.");
        }

        voluntario.setAtivo(false);
        voluntarioRepository.save(voluntario);
    }

    @Transactional
    public void reativar(Long id) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (voluntario.getAtivo()) {
            throw new RegraDeNegocioException("Este voluntário já está ativo.");
        }

        voluntario.setAtivo(true);
        voluntarioRepository.save(voluntario);
    }

    @Transactional(readOnly = true)
    public VoluntarioPerfilResponseDTO obterPerfilCompleto(Long id) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        VoluntarioPerfilResponseDTO perfil = new VoluntarioPerfilResponseDTO();
        perfil.setId(voluntario.getId());
        perfil.setPrimeiroNome(voluntario.getPrimeiroNome());
        perfil.setUltimoNome(voluntario.getUltimoNome());
        perfil.setEmailPessoal(voluntario.getEmailPessoal());
        perfil.setTipoUsuario(voluntario.getTipoUsuario().name());

        if (voluntario.getTipoUsuario() != TipoUsuario.VOLUNTARIO) {
            membroRepository.findById(id).ifPresent(membro -> {
                perfil.setNumeroMembresia(membro.getNumMembresia());
                perfil.setEmailIeee(membro.getEmailIeee());
                perfil.setTipoMembresia(membro.getTipoMembresia().name());
            });
        }

        if (diretorRepository.existsById(id)) {
            boolean usuarioEhDiretor = voluntario.getTipoUsuario() == TipoUsuario.DIRETOR_RAMO
                    || voluntario.getTipoUsuario() == TipoUsuario.DIRETOR_CAPITULO;
            List<MandatoResponseDTO> historico = mandatoService.obterHistoricoMandatos(id, usuarioEhDiretor);
            perfil.setHistoricoMandatos(historico);
        }

        return perfil;
    }

    @Transactional
    public void promoverAMembro(Long id, PromoverMembroRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (!voluntario.getAtivo()) {
            throw new RegraDeNegocioException(
                    "Não é possível promover um voluntário inativo. Reative a conta primeiro.");
        }

        Optional<Membro> membroExistente = membroRepository.findById(id);

        if (membroExistente.isPresent() && membroExistente.get().getDataFim() == null) {
            throw new RegraDeNegocioException("Este voluntário já é um Membro ativo.");
        }

        voluntario.setTipoUsuario(TipoUsuario.MEMBRO);
        voluntarioRepository.save(voluntario);

        Membro membro;
        if (membroExistente.isPresent()) {
            membro = membroExistente.get();
            membro.setDataInicio(LocalDate.now());
            membro.setDataFim(null);
            membro.setNumMembresia(dto.getNumeroMembresia());
            membro.setTipoMembresia(dto.getTipoMembresia());
            String emailIeee = dto.getEmailIeee();
            if (emailIeee != null && emailIeee.trim().isEmpty()) {
                emailIeee = null;
            }
            membro.setEmailIeee(emailIeee);
        } else {
            membro = new Membro();
            membro.setVoluntario(voluntario);
            membro.setNumMembresia(dto.getNumeroMembresia());
            membro.setTipoMembresia(dto.getTipoMembresia());
            membro.setEmailIeee(dto.getEmailIeee());
            membro.setDataInicio(LocalDate.now());
            membro.setDataFim(null);

            voluntario.setMembro(membro);
        }

        membroRepository.save(membro);
    }

    @Transactional
    public void promoverADiretor(Long id, PromoverDiretorRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (!voluntario.getAtivo()) {
            throw new RegraDeNegocioException(
                    "Não é possível promover um voluntário inativo. Reative a conta primeiro.");
        }

        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(
                        "Apenas voluntários com registro de membro podem ser promovidos a Diretor."));

        if (membro.getDataFim() != null) {
            throw new RegraDeNegocioException("Não é possível promover um membro inativo. Ative a membresia primeiro.");
        }

        if (dto.getDataFim().isBefore(dto.getDataInicio())) {
            throw new RegraDeNegocioException("A data de término não pode ser anterior à data de início.");
        }

        voluntario.setTipoUsuario(dto.getTipoDiretor());
        voluntarioRepository.save(voluntario);

        Diretor diretor;
        if (!diretorRepository.existsById(id)) {
            diretor = new Diretor();
            diretor.setMembro(membro);
            diretor = diretorRepository.saveAndFlush(diretor);
        } else {
            diretor = diretorRepository.findById(id).get();
        }

        mandatoService.criarMandato(diretor, dto.getCargoId(), dto.getDataInicio(), dto.getDataFim());
    }

    @Transactional
    public void removerMembresia(Long id) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        Membro membro = membroRepository.findById(id)
                .orElseThrow(
                        () -> new RegraDeNegocioException("Este usuário não possui registro de Membro no sistema."));

        if (voluntario.getTipoUsuario() == TipoUsuario.VOLUNTARIO && membro.getDataFim() != null) {
            throw new RegraDeNegocioException("Este usuário já é um voluntário com membresia inativa.");
        }

        LocalDate hoje = LocalDate.now();

        if (voluntario.getTipoUsuario() == TipoUsuario.DIRETOR_RAMO
                || voluntario.getTipoUsuario() == TipoUsuario.DIRETOR_CAPITULO) {
            removerDiretoria(id);
        }

        membro.setDataFim(hoje);
        membroRepository.save(membro);

        voluntario.setTipoUsuario(TipoUsuario.VOLUNTARIO);
        voluntarioRepository.save(voluntario);
    }

    @Transactional
    public void removerDiretoria(Long id) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (voluntario.getTipoUsuario() != TipoUsuario.DIRETOR_RAMO
                && voluntario.getTipoUsuario() != TipoUsuario.DIRETOR_CAPITULO) {
            throw new RegraDeNegocioException("Este usuário não está atuando como um Diretor ativo.");
        }

        mandatoService.encerrarMandatosAtivosOuFuturos(id);

        voluntario.setTipoUsuario(TipoUsuario.MEMBRO);
        voluntarioRepository.save(voluntario);
    }

    @Transactional
    public void alterarCargoDiretor(Long id, AtualizarCargoRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (voluntario.getTipoUsuario() != TipoUsuario.DIRETOR_RAMO
                && voluntario.getTipoUsuario() != TipoUsuario.DIRETOR_CAPITULO) {
            throw new RegraDeNegocioException("Este usuário não é um Diretor ativo.");
        }

        Diretor diretor = diretorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Diretor não encontrado."));
        LocalDate hoje = LocalDate.now();

        mandatoService.encerrarMandatosAtivosOuFuturos(id);
        mandatoService.criarMandato(diretor, dto.getNovoCargoId(), hoje.plusDays(1), dto.getDataFimNovoMandato());
    }

    @Transactional
    public void alterarSenha(Long id, AlterarSenhaRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), voluntario.getSenha())) {
            throw new RegraDeNegocioException("A senha atual informada está incorreta.");
        }

        voluntario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        voluntarioRepository.save(voluntario);
    }

    @Transactional
    public void adminAtualizarEmailCPF(Long id, AdminUpdateEmailCPFRequestDTO dto) {
        Voluntario voluntario = voluntarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));

        if (dto.getNovoEmail() != null && !dto.getNovoEmail().equalsIgnoreCase(voluntario.getEmailPessoal())) {
            if (voluntarioRepository.findByEmailPessoal(dto.getNovoEmail()).isPresent()) {
                throw new RegraDeNegocioException("O novo e-mail informado já está em uso por outro voluntário.");
            }
            voluntario.setEmailPessoal(dto.getNovoEmail());
        }

        if (dto.getNovoCpf() != null && !dto.getNovoCpf().equals(voluntario.getCpf())) {
            if (voluntarioRepository.findByCpf(dto.getNovoCpf()).isPresent()) {
                throw new RegraDeNegocioException("O novo CPF informado já está em uso por outro voluntário.");
            }
            voluntario.setCpf(dto.getNovoCpf());
        }

        voluntarioRepository.save(voluntario);
    }

    private VoluntarioResponseDTO toResponseDTO(Voluntario voluntario) {
        VoluntarioResponseDTO dto = new VoluntarioResponseDTO();
        dto.setId(voluntario.getId());
        dto.setPrimeiroNome(voluntario.getPrimeiroNome());
        dto.setUltimoNome(voluntario.getUltimoNome());
        dto.setEmailPessoal(voluntario.getEmailPessoal());
        dto.setTipoUsuario(voluntario.getTipoUsuario().name());
        dto.setAtivo(voluntario.getAtivo());
        return dto;
    }
}