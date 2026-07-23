package br.ufrn.ieee.database.infra.security.refreshtoken;

import br.ufrn.ieee.database.shared.exception.EntidadeNaoEncontradaException;
import br.ufrn.ieee.database.shared.exception.RefreshTokenInvalidoException;
import br.ufrn.ieee.database.voluntario.model.Voluntario;
import br.ufrn.ieee.database.voluntario.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
public class RefreshTokenService {

    private static final Duration DURACAO_CURTA = Duration.ofDays(1);
    private static final Duration DURACAO_LONGA = Duration.ofDays(30);

    private final RefreshTokenRepository refreshTokenRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
            VoluntarioRepository voluntarioRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.voluntarioRepository = voluntarioRepository;
    }

    @Transactional
    public String gerarParaEmail(String emailPessoal, boolean manterConectado) {
        Voluntario voluntario = voluntarioRepository.findByEmailPessoal(emailPessoal)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Voluntário não encontrado."));
        return gerar(voluntario, manterConectado);
    }

    @Transactional
    public ResultadoRotacao validarERotacionar(String tokenBruto) {
        RefreshToken tokenAtual = refreshTokenRepository.findByTokenHash(hash(tokenBruto))
                .orElseThrow(() -> new RefreshTokenInvalidoException("Sessão inválida. Faça login novamente."));

        if (tokenAtual.getRevogado()) {
            // Ivalida todas as sessões ativas do usuário, caos encontre um token já
            // rotacionado. Isso é sinal de uso indevido (cookie copiado/roubado).
            revogarTodosDoUsuario(tokenAtual.getVoluntario().getId());
            throw new RefreshTokenInvalidoException("Sessão inválida. Faça login novamente.");
        }

        if (tokenAtual.getDataExpiracao().isBefore(Instant.now())) {
            throw new RefreshTokenInvalidoException("Sessão expirada. Faça login novamente.");
        }

        tokenAtual.setRevogado(true);
        refreshTokenRepository.save(tokenAtual);

        String novoTokenBruto = gerar(tokenAtual.getVoluntario(), tokenAtual.getManterConectado());
        return new ResultadoRotacao(tokenAtual.getVoluntario(), novoTokenBruto, tokenAtual.getManterConectado());
    }

    @Transactional
    public void revogar(String tokenBruto) {
        refreshTokenRepository.findByTokenHash(hash(tokenBruto))
                .ifPresent(token -> {
                    token.setRevogado(true);
                    refreshTokenRepository.save(token);
                });
    }

    private String gerar(Voluntario voluntario, boolean manterConectado) {
        String tokenBruto = gerarTokenAleatorio();
        Duration duracao = manterConectado ? DURACAO_LONGA : DURACAO_CURTA;

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setTokenHash(hash(tokenBruto));
        refreshToken.setVoluntario(voluntario);
        refreshToken.setManterConectado(manterConectado);
        refreshToken.setDataExpiracao(Instant.now().plus(duracao));
        refreshTokenRepository.save(refreshToken);

        return tokenBruto;
    }

    private void revogarTodosDoUsuario(Long voluntarioId) {
        List<RefreshToken> tokensAtivos = refreshTokenRepository.findByVoluntarioIdAndRevogadoFalse(voluntarioId);
        tokensAtivos.forEach(token -> token.setRevogado(true));
        refreshTokenRepository.saveAll(tokensAtivos);
    }

    private String gerarTokenAleatorio() {
        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String tokenBruto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(tokenBruto.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 indisponível na JVM.", e);
        }
    }

    public record ResultadoRotacao(Voluntario voluntario, String novoTokenBruto, boolean manterConectado) {
    }
}