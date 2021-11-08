package server.domain.entity.user.util;

import lombok.Getter;
import lombok.Setter;
import server.domain.entity.user.User;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "refreshToken")
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant exp;

}
