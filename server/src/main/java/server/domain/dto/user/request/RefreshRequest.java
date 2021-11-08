package server.domain.dto.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshRequest {

    @NotBlank
    private String refreshToken;

}
