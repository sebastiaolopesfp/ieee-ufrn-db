package br.ufrn.ieee.database.service;

import br.ufrn.ieee.database.model.Voluntario;
import br.ufrn.ieee.database.repository.VoluntarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final VoluntarioRepository voluntarioRepository;

    public AutenticacaoService(VoluntarioRepository voluntarioRepository) {
        this.voluntarioRepository = voluntarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailPessoal) throws UsernameNotFoundException {
        Voluntario voluntario = voluntarioRepository.findByEmailPessoal(emailPessoal)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + emailPessoal));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + voluntario.getTipoUsuario());

        return new User(
                voluntario.getEmailPessoal(),
                voluntario.getSenha(),
                Collections.singletonList(authority)
        );
    }
}