package com.hocket.modules.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountFactory accountFactory;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void cleanUp(){
        accountRepository.deleteAll();
    }

    @DisplayName("login - cache test")
    @Test
    void cacheTest_put(){

        Account account = accountFactory.createNewAccount("bigave", "test@email.com");
        String token = UUID.randomUUID().toString();
        accountService.login(account.getId(), token);

        assertThat(cacheManager.getCache("account").get(token).get()).isEqualTo(account.getId());

    }
    @DisplayName("logout - cache test")
    @Test
    void cacheTest_evict(){

        String token = UUID.randomUUID().toString();
        Account account = accountFactory.createNewAccount("bigave", "test@email.com");
        accountService.login(account.getId(), token);

        accountService.logout(token);

        assertNull(cacheManager.getCache("account").get(token));

    }

    @DisplayName("save Account")
    @Test
    void save_account() throws JsonProcessingException {

        KakaoUserInfoResponseDto responseDto = new KakaoUserInfoResponseDto();
        responseDto.setKakao_accountGender("male");
        responseDto.setKakao_accountAge_range("20~29");
        responseDto.setKakao_accountProfileNickname("김태준");
        responseDto.setKakao_accountEmail("test@email.com");

        Account account = accountService.saveAccount(responseDto);

        assertNotNull(account);
        assertEquals(account,accountRepository.findByEmail("test@email.com"));



    }
}