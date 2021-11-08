package server.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import server.domain.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean findByName(String name);

}
