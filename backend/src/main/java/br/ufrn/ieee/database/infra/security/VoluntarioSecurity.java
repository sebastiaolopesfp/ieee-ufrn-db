// infra/security/VoluntarioSecurity.java (novo)
package br.ufrn.ieee.database.infra.security;

import br.ufrn.ieee.database.voluntario.repository.VoluntarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("voluntarioSecurity")
public class VoluntarioSecurity {

    private final VoluntarioRepository voluntarioRepository;

    public VoluntarioSecurity(VoluntarioRepository voluntarioRepository) {
        this.voluntarioRepository = voluntarioRepository;
    }

    public boolean isOwner(Long voluntarioId, Authentication authentication) {
        String emailAutenticado = authentication.getName();
        return voluntarioRepository.findById(voluntarioId)
                .map(v -> v.getEmailPessoal().equalsIgnoreCase(emailAutenticado))
                .orElse(false);
    }
}