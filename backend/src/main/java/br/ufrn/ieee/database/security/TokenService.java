package br.ufrn.ieee.database.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // A assinatura secreta deve ficar no seu application.properties / application.yml
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(String email, String role) {
        try {
            // Define o algoritmo de assinatura com a chave secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);
            
            // Cria o token JWT injetando as informações do usuário (Claims)
            return JWT.create()
                    .withIssuer("ieee-database-api") // Identifica quem gerou o token
                    .withSubject(email)               // Identifica o dono do token
                    .withClaim("role", role)          // Salva o cargo (ADMIN/VOLUNTARIO) dentro do token
                    .withExpiresAt(gerarDataExpiracao()) // Define o tempo de expiração
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ieee-database-api")
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o e-mail do usuário se o token for válido
        } catch (JWTVerificationException exception) {
            return null; // Retorna null se o token estiver corrompido ou expirado
        }
    }

    // Define que o token expira em 2 horas (baseado no fuso horário do Brasil/Natal)
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}