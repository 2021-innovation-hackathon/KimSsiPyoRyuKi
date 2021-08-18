package com.hocket.modules.main;

import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountFactory;
import com.hocket.modules.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Autowired
    CacheManager cacheManager;


    @BeforeEach
    void cleanUp(){
        accountRepository.deleteAll();
        cacheManager.getCache("account").clear();
    }


    @DisplayName("로그인 테스트")
    @Test
    void login() throws Exception {

        String token = UUID.randomUUID().toString();

        Account account = accountFactory.createNewAccount("bigave", "test@email.com");

        mockMvc.perform(post("/login")
                .param("nickname", "bigave")
                .param("name", "김태준")
                .param("email", "test@email.com")
                .param("token", token)
                .with(csrf()))
                .andExpect(status().isOk());

        assertThat(cacheManager.getCache("account").get(token).get()).isEqualTo(account.getId());

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