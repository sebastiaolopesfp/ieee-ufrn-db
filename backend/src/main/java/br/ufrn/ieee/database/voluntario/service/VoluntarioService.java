package br.ufrn.ieee.database.voluntario.service;

import br.ufrn.ieee.database.shared.exception.RegraDeNegocioException;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioRequestDTO;
import br.ufrn.ieee.database.voluntario.dto.VoluntarioResponseDTO;
import br.ufrn.ieee.database.voluntario.model.TipoUsuario;
import br.ufrn.ieee.database.voluntario.model.Voluntario;
import br.ufrn.ieee.database.voluntario.repository.VoluntarioRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public VoluntarioService(VoluntarioRepository voluntarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.voluntarioRepository = voluntarioRepository;
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
}