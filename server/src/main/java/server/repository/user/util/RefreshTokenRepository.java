package server.repository.user.util;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.entity.user.util.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

}
