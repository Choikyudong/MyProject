package server.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import server.api.UserApi;
import server.service.user.UserService;

@WebMvcTest(UserApi.class)
public class UserSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @DisplayName("MockMvc 테스트, 접근 제한없음")
    @Test
    public void test1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("접근권한 테스트, USER 권한을 요구함")
    @Test
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test2"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

//    @DisplayName("회원 로그인")
//    @Test
//    public void test3() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/common/signup"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo();
//    }

}
