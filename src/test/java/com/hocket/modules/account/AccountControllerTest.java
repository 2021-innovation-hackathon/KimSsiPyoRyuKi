package com.hocket.modules.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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


    @DisplayName("회원 가입 테스트 - 정상 입력")
    @Test
    void signUp_with_correct_input() throws Exception {

        String token = UUID.randomUUID().toString();

        Account accountData = new Account();
        accountData.setName("김태준");
        accountData.setEmail("test@email.com");
        accountData.setId(1L);

        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(accountData));
        when(accountService.getInfoByToken(token)).thenReturn(jsonNode);
        when(accountService.checkToken(token)).thenReturn(true);
        when(accountService.saveAccount(jsonNode, "bigave")).thenReturn(accountData);

        mockMvc.perform(post("/sign-up")
                .param("nickname", "bigave")
                .param("token", token)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));

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
                .andExpect(content().string("exists.nickname"));

    }

    @DisplayName("회원 가입 테스트 - 이미 존재하는 이메일")
    @Test
    void signUp_exists_email() throws Exception {

        String token = UUID.randomUUID().toString();
        Account account = accountFactory.createNewAccount("bigave", "test@email.com");

        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(account));
        when(accountService.getInfoByToken(token)).thenReturn(jsonNode);
        when(accountService.checkToken(token)).thenReturn(true);


        mockMvc.perform(post("/sign-up")
                .param("nickname", "bigave2")
                .param("token", token)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("exists.email"));

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
                .andExpect(content().string("wrong.nickname"));

        assertFalse(accountRepository.existsByNickname("--wrong"));

    }

    @DisplayName("계정 정보 가져오기")
    @Test
    void getAccountInfo() throws Exception {

        String token = UUID.randomUUID().toString();

        Account account = accountFactory.createNewAccount("bigave", "test@email.com");
        cacheManager.getCache("account").put(token, account.getId());

        mockMvc.perform(get("/account/info/"+token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", is(equalTo("bigave"))));

    }


}