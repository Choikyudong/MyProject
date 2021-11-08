package server.domain.dto.user.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {

    @Builder
    public SignUpForm(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    @NotBlank
    @Email(message = "아이디는 이메일 형식입니다.")
    @Size(max = 50)
    String username;

    @NotBlank
    @Pattern(regexp = "(^[a-zA-Z0-9]*$)",
            message = "비밀번호를 꼭 입력해야 합니다."
    )
    String password;

    @NotBlank
    @Size(max = 30)
    String name;

}
