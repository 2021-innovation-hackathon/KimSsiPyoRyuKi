package com.hocket.modules.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountFactory;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.kakao.KakaoService;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
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
    KakaoService kakaoService;

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

        KakaoUserInfoResponseDto responseDto = new KakaoUserInfoResponseDto();
        responseDto.setNickname(account.getNickname());
        responseDto.setAgeRange(account.getAgeRange());
        responseDto.setGender(account.getGender());
        responseDto.setEmail(account.getEmail());

        when(kakaoService.checkToken(token)).thenReturn(true);
        when(kakaoService.getInfoByToken(token)).thenReturn(responseDto);

        mockMvc.perform(post("/login")
                .param("token", token))
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 테스트 - 가입되지 않은 이메일")
    @Test
    void login_wrong_user() throws Exception {

        String token = UUID.randomUUID().toString();


        KakaoUserInfoResponseDto responseDto = new KakaoUserInfoResponseDto();
        responseDto.setNickname("김태준");
        responseDto.setAgeRange("20~29");
        responseDto.setNickname("김태준");
        responseDto.setEmail("test@emai.com");

        when(kakaoService.getInfoByToken(token)).thenReturn(responseDto);
        when(kakaoService.checkToken(token)).thenReturn(true);

        mockMvc.perform(post("/login")
                .param("token", token))
                .andExpect(status().isNotFound());
    }



}