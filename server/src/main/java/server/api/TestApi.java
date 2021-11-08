package server.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import server.config.CustomUserDetail;
import server.domain.dto.user.request.RefreshRequest;
import server.domain.dto.user.response.RefreshResponse;
import server.domain.entity.user.util.RefreshToken;
import server.exception.TokenRefreshException;
import server.repository.user.util.RefreshTokenRepository;
import server.util.JwtProvider;
import server.util.RefreshTokenProvider;

import javax.validation.Valid;

@Api(tags = "RestApi 테스트용입니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestApi {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CustomUserDetail customUserDetail;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenProvider refreshTokenProvider;

    @GetMapping("/all")
    public String test1() {
        return "테스트용";
    }

    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String test2() {
        return "회원이 아니면 접근불가입니다.";
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        return refreshTokenRepository.findByToken(refreshToken)
                .map(refreshTokenProvider::verifyExp)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.createRefreshToken(user);
                    return ResponseEntity.ok(new RefreshResponse(token, refreshToken));
                }).orElseThrow(() -> new TokenRefreshException(
                        refreshToken, "이거 DB에 없는데용?"
                ));

    }
}
