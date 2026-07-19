package br.ufrn.ieee.database.infra.security;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufrn.ieee.database.shared.dto.ErroResponseDTO;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private static final String CAMINHO_LOGIN = "/api/auth/login";
    private static final int TENTATIVAS_PERMITIDAS = 5;
    private static final Duration JANELA_DE_TEMPO = Duration.ofMinutes(1);

    // Mapa em memória, válido apenas com uma única instância rodando. Em
    // caso de escala horizontal, migrar para um armazenamento compartilhado
    // (ex: Redis) — senão cada instância passa a ter seu próprio limite.
    private final ConcurrentHashMap<String, Bucket> tentativasPorIp = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean ehTentativaDeLogin = "POST".equalsIgnoreCase(request.getMethod())
                && CAMINHO_LOGIN.equals(request.getRequestURI());

        if (!ehTentativaDeLogin) {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = extrairIpDoCliente(request);
        Bucket bucket = tentativasPorIp.computeIfAbsent(ip, chave -> criarNovoBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
            return;
        }

        responderComLimiteExcedido(response);
    }

    private Bucket criarNovoBucket() {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(TENTATIVAS_PERMITIDAS).refillGreedy(TENTATIVAS_PERMITIDAS,
                        JANELA_DE_TEMPO))
                .build();
    }

    private void responderComLimiteExcedido(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErroResponseDTO erro = new ErroResponseDTO(
                "Muitas tentativas de login. Aguarde um minuto antes de tentar novamente.",
                HttpStatus.TOO_MANY_REQUESTS.value());

        response.getWriter().write(objectMapper.writeValueAsString(erro));
    }

    // Atrás de um proxy reverso (padrão em plataformas de deploy como o
    // Render), getRemoteAddr() retorna o IP do proxy — o IP real do
    // cliente vem no header X-Forwarded-For.
    private String extrairIpDoCliente(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}