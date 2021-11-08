package server.domain.dto.user.response;

import lombok.Builder;
import lombok.Data;
import server.domain.entity.user.util.Role;

@Data
public class JwtResponse {

    @Builder
    public JwtResponse(String token, String refreshToken,String username, String name, Role role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.name = name;
        this.role = role;
    }

    private String token;
    private String refreshToken;
    private String username;
    private String name;
    private Role role;

}
