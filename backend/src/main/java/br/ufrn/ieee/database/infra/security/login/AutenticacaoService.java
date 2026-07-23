package br.ufrn.ieee.database.infra.security.login;

import br.ufrn.ieee.database.voluntario.model.Voluntario;
import br.ufrn.ieee.database.voluntario.repository.VoluntarioRepository;
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
                .orElseThrow(
                        () -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + emailPessoal));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + voluntario.getTipoUsuario().name());

        return new User(
                voluntario.getEmailPessoal(),
                voluntario.getSenha(),
                voluntario.getAtivo(),
                true,
                true,
                true,
                Collections.singletonList(authority));
    }
}