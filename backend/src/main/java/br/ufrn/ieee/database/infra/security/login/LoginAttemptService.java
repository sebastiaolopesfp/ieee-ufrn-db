package br.ufrn.ieee.database.infra.security.login;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import br.ufrn.ieee.database.shared.exception.ContaTemporariamenteBloqueadaException;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.EstimationProbe;
import org.springframework.stereotype.Component;

@Component
public class LoginAttemptService {

    private static final int TENTATIVAS_ANTES_DO_BLOQUEIO = 3;
    private static final Duration DURACAO_DO_BLOQUEIO = Duration.ofMinutes(15);

    // Mapa em memória, válido apenas com uma única instância rodando. Como
    // a chave é o e-mail digitado (sem validação prévia), um atacante
    // distribuído poderia inflar esse mapa indefinidamente, se isso virar
    // problema real, trocar por um cache com limite e expiração (Bucket4j
    // tem integração nativa com Caffeine para isso).
    private final ConcurrentHashMap<String, Bucket> tentativasPorEmail = new ConcurrentHashMap<>();

    public void validarNaoBloqueado(String email) {
        Bucket bucket = obterBucket(email);
        EstimationProbe estimativa = bucket.estimateAbilityToConsume(1);

        if (!estimativa.canBeConsumed()) {
            long minutosRestantes = Duration.ofNanos(estimativa.getNanosToWaitForRefill()).toMinutes() + 1;
            throw new ContaTemporariamenteBloqueadaException(
                    "Conta temporariamente bloqueada por excesso de tentativas. Tente novamente em "
                            + minutosRestantes + " minuto(s).");
        }
    }

    public void registrarTentativaFalha(String email) {
        obterBucket(email).tryConsume(1);
    }

    // Só falhas consecutivas contam: um login correto zera o histórico de erros.
    public void registrarLoginComSucesso(String email) {
        tentativasPorEmail.remove(normalizar(email));
    }

    private Bucket obterBucket(String email) {
        return tentativasPorEmail.computeIfAbsent(normalizar(email), chave -> criarNovoBucket());
    }

    private Bucket criarNovoBucket() {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(TENTATIVAS_ANTES_DO_BLOQUEIO)
                        .refillIntervally(TENTATIVAS_ANTES_DO_BLOQUEIO, DURACAO_DO_BLOQUEIO))
                .build();
    }

    private String normalizar(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}