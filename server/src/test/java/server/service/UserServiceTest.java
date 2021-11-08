package server.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import server.domain.dto.user.request.SignInForm;
import server.domain.dto.user.request.SignUpForm;
import server.domain.dto.user.request.UpdateForm;
import server.domain.entity.user.User;
import server.domain.entity.user.util.Role;
import server.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceTest {

    @Autowired(required = true)
    private UserRepository userRepository;

    @BeforeEach
    void 시작() {
        System.out.println("====================테스트 시작====================");
        User user = User.builder()
                .username("Tester@naver.com")
                .password("12345")
                .name("테스트유저")
                .enabled(true)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void 끝() {
        System.out.println("====================테스트 끝=====================");
    }

    @DisplayName("유저가 생성 테스트 및 확인")
    @Test
    void test1() {
        //given
        SignUpForm signUpForm = SignUpForm.builder()
                .username("slgmgm@naver.com")
                .password("12345")
                .name("유저")
                .build();

        //when
        User user = User.builder()
                .username(signUpForm.getUsername())
                .password(signUpForm.getPassword())
                .name(signUpForm.getName())
                .enabled(true)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        //then
        Optional<User> getUser = userRepository.findByUsername("slgmgm@naver.com");
        assertThat(user, is(getUser.get()));
    }

    @DisplayName("전체 유저목록 확인 그리고 해당 유저로 로그인 실험")
    @Test
    void test2() {
        //given
        List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println("userList : " + user.toString()));

        //when
        SignInForm signInForm = SignInForm.builder()
                .username("Tester@naver.com")
                .password("12345")
                .build();

        //then
        Optional<User> getUser = userRepository.findByUsername("Tester@naver.com");

        SignInForm signInForm1 = SignInForm.builder().username("Tester@naver.com").build();
        signInForm1.setUsername(getUser.orElseGet(null).getUsername());

        assertThat(signInForm.getUsername(), is(signInForm1.getUsername()));
    }

    @DisplayName("유저 정보 업데이트")
    @Test
    void test3() {
        // given
        UpdateForm updateForm = UpdateForm.builder()
                .password("54321")
                .name("변경된이름입니당")
                .build();

        // when
        Optional<User> user = userRepository.findByUsername("Tester@naver.com");
//        user.setPassword(updateForm.getPassword());
//        user.setName(updateForm.getName());

        // then
        System.out.println("결과물 출력해보기 -> " + user);
    }

    @DisplayName("유저 정보 삭제")
    @Test
    void test4() {
        // given
        Optional<User> user = userRepository.findByUsername("Tester@naver.com");

        // when
        userRepository.delete(user.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저는 없습니다.")));

        // then
        assertThat(userRepository.findByUsername("Tester@naver.com"), is(Optional.empty()));
    }

}
