package br.ufrn.ieee.database.infra.security;

import br.ufrn.ieee.database.shared.dto.LoginRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmailPessoal(), dto.getSenha());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String role = principal.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        // Gera o Token JWT contendo o e-mail e a Role
        String token = tokenService.gerarToken(dto.getEmailPessoal(), role);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("tipo", "Bearer");

        return ResponseEntity.ok(response);
    }
}