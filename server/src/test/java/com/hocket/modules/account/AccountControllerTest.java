package com.hocket.modules.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hocket.modules.hocket.HocketRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
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
    @Autowired
    HocketRepository hocketRepository;
    @MockBean
    KakaoService kakaoService;

    @BeforeEach
    void cleanUp(){
        hocketRepository.deleteAll();
        accountRepository.deleteAll();
    }


    @DisplayName("회원 가입 테스트 - 정상 입력")
    @Test
    void signUp_with_correct_input() throws Exception {

        String token = UUID.randomUUID().toString();

        Account accountData = new Account();
        accountData.setEmail("test@email.com");
        accountData.setNickname("김태준");
        accountData.setAgeRange("20~29");

        KakaoUserInfoResponseDto responseDto = new KakaoUserInfoResponseDto();
        responseDto.setGender("male");
        responseDto.setAgeRange("20~29");
        responseDto.setNickname("김태준");
        responseDto.setEmail("test@email.com");

        when(kakaoService.getInfoByToken(token)).thenReturn(responseDto);
        when(kakaoService.checkToken(token)).thenReturn(true);
        when(accountService.saveAccount(responseDto)).thenReturn(accountData);

        mockMvc.perform(post("/sign-up")
                .param("token", token))
                .andExpect(status().isOk());

    }

    @DisplayName("회원 가입 테스트 - 이미 존재하는 이메일")
    @Test
    void signUp_exists_email() throws Exception {

        String token = UUID.randomUUID().toString();
        Account account = accountFactory.createNewAccount("bigave", "test@email.com");

        KakaoUserInfoResponseDto responseDto = new KakaoUserInfoResponseDto();
        responseDto.setGender("male");
        responseDto.setAgeRange("20~29");
        responseDto.setNickname("김태준");
        responseDto.setEmail("test@email.com");

        when(kakaoService.getInfoByToken(token)).thenReturn(responseDto);
        when(kakaoService.checkToken(token)).thenReturn(true);


        mockMvc.perform(post("/sign-up")
                .param("token", token))
                .andExpect(status().is4xxClientError());

    }
}