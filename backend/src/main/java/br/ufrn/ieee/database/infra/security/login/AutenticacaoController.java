package br.ufrn.ieee.database.infra.security.login;

import br.ufrn.ieee.database.infra.security.jwt.TokenService;
import br.ufrn.ieee.database.infra.security.refreshtoken.RefreshTokenService;
import br.ufrn.ieee.database.shared.dto.LoginRequestDTO;
import br.ufrn.ieee.database.shared.exception.RefreshTokenInvalidoException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    private static final String NOME_COOKIE_REFRESH = "refreshToken";
    private static final String PATH_COOKIE_REFRESH = "/api/auth";
    private static final Duration DURACAO_COOKIE_CURTA = Duration.ofDays(1);
    private static final Duration DURACAO_COOKIE_LONGA = Duration.ofDays(30);

    // Configurável por ambiente: em produção (Render, HTTPS) deve ser
    // true/None; em desenvolvimento local (HTTP) precisa ser false/Lax,
    // senão o navegador simplesmente descarta o cookie. Ver application.properties.
    @Value("${app.security.cookie-secure:false}")
    private boolean cookieSecure;

    @Value("${app.security.cookie-samesite:Lax}")
    private String cookieSameSite;

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final LoginAttemptService loginAttemptService;
    private final RefreshTokenService refreshTokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService,
            LoginAttemptService loginAttemptService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.loginAttemptService = loginAttemptService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto, HttpServletResponse response) {
        loginAttemptService.validarNaoBloqueado(dto.getEmailPessoal());

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmailPessoal(), dto.getSenha());
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);

            loginAttemptService.registrarLoginComSucesso(dto.getEmailPessoal());

            var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
            String role = principal.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

            String accessToken = tokenService.gerarToken(dto.getEmailPessoal(), role);

            boolean manterConectado = Boolean.TRUE.equals(dto.getManterConectado());
            String refreshTokenBruto = refreshTokenService.gerarParaEmail(dto.getEmailPessoal(), manterConectado);
            Duration duracaoCookie = manterConectado ? DURACAO_COOKIE_LONGA : DURACAO_COOKIE_CURTA;
            response.addHeader(HttpHeaders.SET_COOKIE,
                    criarCookieRefreshToken(refreshTokenBruto, duracaoCookie).toString());

            return ResponseEntity.ok(montarCorpoToken(accessToken));
        } catch (BadCredentialsException ex) {
            loginAttemptService.registrarTentativaFalha(dto.getEmailPessoal());
            throw ex;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(name = NOME_COOKIE_REFRESH, required = false) String refreshTokenCookie,
            HttpServletResponse response) {

        if (refreshTokenCookie == null || refreshTokenCookie.isBlank()) {
            throw new RefreshTokenInvalidoException("Sessão inválida. Faça login novamente.");
        }

        RefreshTokenService.ResultadoRotacao resultado = refreshTokenService.validarERotacionar(refreshTokenCookie);
        var voluntario = resultado.voluntario();

        String accessToken = tokenService.gerarToken(voluntario.getEmailPessoal(), voluntario.getTipoUsuario().name());

        Duration duracaoCookie = resultado.manterConectado() ? DURACAO_COOKIE_LONGA : DURACAO_COOKIE_CURTA;
        response.addHeader(HttpHeaders.SET_COOKIE,
                criarCookieRefreshToken(resultado.novoTokenBruto(), duracaoCookie).toString());

        return ResponseEntity.ok(montarCorpoToken(accessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = NOME_COOKIE_REFRESH, required = false) String refreshTokenCookie,
            HttpServletResponse response) {

        if (refreshTokenCookie != null && !refreshTokenCookie.isBlank()) {
            refreshTokenService.revogar(refreshTokenCookie);
        }

        // maxAge zero instrui o navegador a apagar o cookie imediatamente.
        response.addHeader(HttpHeaders.SET_COOKIE, criarCookieRefreshToken("", Duration.ZERO).toString());
        return ResponseEntity.noContent().build();
    }

    private ResponseCookie criarCookieRefreshToken(String valor, Duration duracao) {
        return ResponseCookie.from(NOME_COOKIE_REFRESH, valor)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite(cookieSameSite)
                .path(PATH_COOKIE_REFRESH)
                .maxAge(duracao)
                .build();
    }

    private Map<String, String> montarCorpoToken(String accessToken) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put("token", accessToken);
        corpo.put("tipo", "Bearer");
        return corpo;
    }
}