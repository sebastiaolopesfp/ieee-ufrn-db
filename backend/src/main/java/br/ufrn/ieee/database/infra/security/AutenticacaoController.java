package br.ufrn.ieee.database.infra.security;

import br.ufrn.ieee.database.shared.dto.LoginRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final LoginAttemptService loginAttemptService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService,
            LoginAttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.loginAttemptService = loginAttemptService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        loginAttemptService.validarNaoBloqueado(dto.getEmailPessoal());

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmailPessoal(), dto.getSenha());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);

            loginAttemptService.registrarLoginComSucesso(dto.getEmailPessoal());

            var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            String role = principal.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

            String token = tokenService.gerarToken(dto.getEmailPessoal(), role);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("tipo", "Bearer");

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            // Registra a falha e repassa o erro adiante sem alterar a resposta original.
            loginAttemptService.registrarTentativaFalha(dto.getEmailPessoal());
            throw ex;
        }
    }
}