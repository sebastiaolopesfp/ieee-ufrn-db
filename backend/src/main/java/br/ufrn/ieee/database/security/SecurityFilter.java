package br.ufrn.ieee.database.security;

import br.ufrn.ieee.database.repository.VoluntarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final VoluntarioRepository voluntarioRepository;

    public SecurityFilter(TokenService tokenService, VoluntarioRepository voluntarioRepository) {
        this.tokenService = tokenService;
        this.voluntarioRepository = voluntarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        var token = this.recoverToken(request);
        
        if (token != null) {
            var email = tokenService.validarToken(token);
            
            if (email != null) {
                UserDetails user = voluntarioRepository.findByEmailPessoal(email)
                        .map(voluntario -> org.springframework.security.core.userdetails.User.withUsername(voluntario.getEmailPessoal())
                                .password(voluntario.getSenha())
                                .roles(voluntario.getTipoUsuario())
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}