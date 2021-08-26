package com.hocket.modules.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.exception.BadRequestException;
import com.hocket.modules.kakao.KakaoService;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CacheManager cacheManager;
    private final KakaoService kakaoService;


    public Account saveAccount(KakaoUserInfoResponseDto userInfo) {

        Account newAccount = accountRepository.save(userInfo.toEntity());

        return newAccount;
    }

    @CachePut(cacheNames = "account", key = "#token")
    public Long login(Long accountId, String token){
        //save Cache <Token, account Id>

        return  accountId;
    }

    @CacheEvict(cacheNames = "account", key = "#token")
    public void logout(String token){
        //remove Cache findBy token
    }

    public Long getAccountIdByToken(String token) {
        kakaoService.checkToken(token);

        Cache.ValueWrapper account = cacheManager.getCache("account").get(token);
        if(account != null){
            return (Long) account.get();
        }

        KakaoUserInfoResponseDto accountInfo = kakaoService.getInfoByToken(token);
        String email = accountInfo.getKakao_accountEmail();


        if(email == null){
            throw new IllegalArgumentException("email 정보가 동의되지 않았습니다.");
        }
        Account byEmail = accountRepository.findByEmail(email);
        if(byEmail == null){
            throw new IllegalArgumentException("회원가입 되지 않은 이메일 입니다.");
        }

        return byEmail.getId();
    }
}
