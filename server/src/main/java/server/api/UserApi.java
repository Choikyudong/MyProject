package server.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.domain.dto.user.request.SignInForm;
import server.domain.dto.user.request.SignUpForm;
import server.domain.dto.user.request.UpdateForm;
import server.repository.user.util.RefreshTokenRepository;
import server.service.user.UserService;
import server.util.RefreshTokenProvider;

import javax.validation.Valid;
import java.net.URI;

@Api(tags = "회원과 관련된 기능을 제어")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApi {

    private final UserService userService;

    @ApiOperation(value = "회원 가입", notes = "모든 유저들이 접근가능한 회원가입기능")
    @PostMapping("/signup")
    public ResponseEntity<?> SignUp(@Valid @RequestBody SignUpForm signUpForm) {
        URI resultURi = userService.saveUser(signUpForm);
        return ResponseEntity.created(resultURi).body("성공적으로 회원가입됨!");
    }

    @ApiOperation(value = "회원 로그인", notes = "모든 유저들이 접근가능한 로그인기능")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
        return ResponseEntity.ok(userService.loginUser(signInForm));
    }

    @ApiOperation(value = "회원 정보수정", notes = "로그인이 완료된 유저가 정보를 수정")
    @PostMapping("/update")
    public ResponseEntity<?> login(@Valid @RequestBody UpdateForm updateForm) {
        return ResponseEntity.ok(userService.updateUser(updateForm));
    }

}
