package br.ufrn.ieee.database.infra.security.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    List<RefreshToken> findByVoluntarioIdAndRevogadoFalse(Long voluntarioId);
}