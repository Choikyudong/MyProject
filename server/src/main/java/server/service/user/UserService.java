package server.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import server.domain.dto.user.request.SignInForm;
import server.domain.dto.user.request.SignUpForm;
import server.domain.dto.user.request.UpdateForm;
import server.domain.dto.user.response.JwtResponse;
import server.domain.entity.user.User;
import server.domain.entity.user.util.RefreshToken;
import server.domain.entity.user.util.Role;
import server.repository.user.UserRepository;
import server.util.JwtProvider;
import server.util.RefreshTokenProvider;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public Optional<User> checkUser(String username) {
        return userRepository.findByUsername(username);
    }

    public URI saveUser(SignUpForm signUpForm) {
        URI uri;
        if (signUpForm != null) {
            try {
                if (checkUser(signUpForm.getUsername()).isPresent()) {
                    throw new Exception("이미 존재합니다.");
                }
                User user = User.builder()
                        .username(signUpForm.getUsername())
                        .password(encoder.encode(signUpForm.getPassword()))
                        .name(signUpForm.getName())
                        .enabled(true)
                        .role(Role.ROLE_USER)
                        .build();
                userRepository.save(user);
                uri = ServletUriComponentsBuilder
                        .fromCurrentContextPath().path("성공주소")
                        .buildAndExpand().toUri();
                return uri;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("실패주소")
                .buildAndExpand().toUri();
        return uri;
    }

    public JwtResponse loginUser(SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInForm.getUsername(),
                        signInForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.createAcessToken(authentication);
        User user = (User) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenProvider.createRefreshToken(user.getId());
        return JwtResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public URI updateUser(UpdateForm updateForm) {
        URI uri;
        if (updateForm != null) {
            try {
                if (!(userRepository.findByName(updateForm.getName()))) {
                    throw new Exception("없는 아이디");
                }
                User user = User.builder()
                        .password(encoder.encode(updateForm.getPassword()))
                        .name(updateForm.getName())
                        .build();
                userRepository.save(user);
                uri = ServletUriComponentsBuilder
                        .fromCurrentContextPath().path("성공주소")
                        .buildAndExpand().toUri();
                return uri;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("실패주소")
                .buildAndExpand().toUri();
        return uri;
    }

    public void dropUser(Long user_id) {
        Optional<User> getUser = userRepository.findById(user_id);
        getUser.ifPresent(userRepository::delete);
    }

}
