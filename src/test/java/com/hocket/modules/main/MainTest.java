package com.hocket.modules.main;

import com.hocket.modules.account.AccountFactory;
import com.hocket.modules.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainTest {

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


    @DisplayName("로그인 테스트")
    @Test
    void login() throws Exception {

        accountFactory.createNewAccount("bigave", "test@email.com");

        mockMvc.perform(post("/login")
                .param("nickname", "bigave")
                .param("name", "김태준")
                .param("email", "test@email.com")
                .param("token", UUID.randomUUID().toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("bigave"));

    }

    @DisplayName("로그인 테스트 - 가입되지 않은 이메일")
    @Test
    void login_wrong_user() throws Exception {


        mockMvc.perform(post("/login")
                .param("nickname", "bigave")
                .param("name", "김태준")
                .param("email", "test@email.com")
                .param("token", UUID.randomUUID().toString())
                .with(csrf()))
                .andExpect(status().isNotFound());

    }
}