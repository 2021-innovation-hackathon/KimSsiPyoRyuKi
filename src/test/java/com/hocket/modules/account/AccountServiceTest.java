package com.hocket.modules.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        String token = UUID.randomUUID().toString();

        Account accountData = new Account();
        accountData.setEmail("test@email.com");
        accountData.setNickname("김태준");
        accountData.setAgeRange("20~29");

        Map<String, String> kakaoData =new HashMap<>();
        kakaoData.put("email", accountData.getEmail());
        kakaoData.put("nickname", accountData.getNickname());
        kakaoData.put("age_range", accountData.getAgeRange());

        JsonNode kakaoNode = objectMapper.convertValue(kakaoData, JsonNode.class);

        Account account = accountService.saveAccount(kakaoNode);

        assertNotNull(account);
        assertEquals(account,accountRepository.findByEmail("test@email.com"));



    }
}