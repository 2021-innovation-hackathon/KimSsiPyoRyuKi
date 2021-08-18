package com.hocket.modules.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountFactory;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountFactory accountFactory;

    @Autowired
    CacheManager cacheManager;

    @MockBean
    AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void cleanUp(){
        accountRepository.deleteAll();
    }


    @DisplayName("로그인 테스트")
    @Test
    void login() throws Exception {

        String token = UUID.randomUUID().toString();

        Account account = accountFactory.createNewAccount("bigave","test@email.com");

        when(accountService.checkToken(token)).thenReturn(true);
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(account));
        when(accountService.getInfoByToken(token)).thenReturn(jsonNode);


        mockMvc.perform(post("/login")
                .param("token", token)
                .with(csrf()))
                .andExpect(status().isOk());

    }

    @DisplayName("로그인 테스트 - 가입되지 않은 이메일")
    @Test
    void login_wrong_user() throws Exception {

        String token = UUID.randomUUID().toString();

        Account account = new Account();
        account.setNickname("bigave");
        account.setName("김태준");
        account.setEmail("test@email.com");

        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(account));
        when(accountService.getInfoByToken(token)).thenReturn(jsonNode);
        when(accountService.checkToken(token)).thenReturn(true);

        mockMvc.perform(post("/login")
                .param("token", token)
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

}