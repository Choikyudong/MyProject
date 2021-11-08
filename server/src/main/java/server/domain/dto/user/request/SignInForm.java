package server.domain.dto.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class SignInForm {

    @Builder
    public SignInForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Email(message = "아이디는 이메일 형식입니다.")
    String username;

    @NotBlank
    @Pattern(regexp = "(^[a-zA-Z0-9]*$)",
            message = "비밀번호를 꼭 입력해야 합니다."
    )
    String password;

}
