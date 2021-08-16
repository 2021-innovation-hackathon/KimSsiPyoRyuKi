package com.hocket.modules.account;

import com.hocket.modules.account.form.AccountForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountFactory accountFactory;

    @BeforeEach
    void cleanUp(){
        accountRepository.deleteAll();
    }


    @DisplayName("회원 가입 테스트 - 정상 입력")
    @Test
    void signUp_with_correct_input() throws Exception {

        mockMvc.perform(post("/sign-up")
                .param("nickname", "bigave")
                .param("name", "김태준")
                .param("email", "test@email.com")
                .param("token", UUID.randomUUID().toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ok"))
                .andExpect(authenticated().withUsername("bigave"));


        assertTrue(accountRepository.existsByNickname("bigave"));
    }

    @DisplayName("회원 가입 테스트 - 이미 존재하는 닉네임")
    @Test
    void signUp_exists_nickname() throws Exception {

        accountFactory.createNewAccount("bigave", "test@email.com");

        mockMvc.perform(post("/sign-up")
                .param("nickname", "bigave")
                .param("name", "김태준")
                .param("email", "test2@email.com")
                .param("token", UUID.randomUUID().toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("exists.nickname"));

    }

    @DisplayName("회원 가입 테스트 - 이미 존재하는 이메일")
    @Test
    void signUp_exists_email() throws Exception {

        accountFactory.createNewAccount("bigave", "test@email.com");

        mockMvc.perform(post("/sign-up")
                .param("nickname", "bigave2")
                .param("name", "김태준")
                .param("email", "test@email.com")
                .param("token", UUID.randomUUID().toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("exists.email"));

    }

    @DisplayName("회원 가입 테스트 - 잘못된 닉네임 형식")
    @Test
    void signUp_wrong_nickname() throws Exception {

        mockMvc.perform(post("/sign-up")
                .param("nickname", "--wrong")
                .param("name", "김태준")
                .param("email", "test@email.com")
                .param("token", UUID.randomUUID().toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("wrong.nickname"));

        assertFalse(accountRepository.existsByNickname("--wrong"));

    }


}