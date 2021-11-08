package server.domain.dto.user.response;

import lombok.Data;

@Data
public class RefreshResponse {

    public RefreshResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    private String token;
    private String refreshToken;

}
