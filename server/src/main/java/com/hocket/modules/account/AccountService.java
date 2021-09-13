package com.hocket.modules.account;

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
        Long accountId = getAccountIdFromCache(token);
        if(accountId != null){
            return accountId;
        }

        return getAccountIdFromDB(token);
    }

    private Long getAccountIdFromCache(String token) {
        return (Long) cacheManager.getCache("account").get(token).get();
    }

    private Long getAccountIdFromDB(String token) {
        String email = getEmailByToken(token);
        Account byEmail = accountRepository.findByEmail(email);
        if(byEmail == null){
            throw new IllegalArgumentException("회원가입 되지 않은 이메일 입니다.");
        }
        return byEmail.getId();
    }

    private String getEmailByToken(String token) {
        KakaoUserInfoResponseDto accountInfo = kakaoService.getInfoByToken(token);
        String email = accountInfo.getEmail();
        return email;
    }

    public void createAccountAndLogin(String token) {
        KakaoUserInfoResponseDto userInfo = ValidationAndGetUserInfo(token);
        infoValidation(userInfo);
        
        Account newAccount = saveAccount(userInfo);
        login(newAccount.getId(),token);
    }

    private void infoValidation (KakaoUserInfoResponseDto userInfo) {
        checkUserAgreement(userInfo);
        isExistsEmail(userInfo.getEmail());
    }

    private void isExistsEmail(String email) {
        if(accountRepository.existsByEmail(email)){
            log.info("exists email");
            throw new IllegalArgumentException("이미 가입된 정보입니다.");
        }
    }

    private void checkUserAgreement(KakaoUserInfoResponseDto userInfo) {
        if(userInfo.getEmail() == null){
            log.info("null email");
            throw new IllegalArgumentException("이메일 정보 제공 비동의.");
        }
        if(userInfo.getNickname() == null){
            log.info("null nickname");
            throw new IllegalArgumentException("닉네임 정보 제공 비동의");
        }
        if(userInfo.getAgeRange() == null){
            log.info("null age");
            throw new IllegalArgumentException("나이 정보 제공 비동의");
        }
    }
    private KakaoUserInfoResponseDto ValidationAndGetUserInfo(String token) {
        kakaoService.checkToken(token);
        KakaoUserInfoResponseDto userInfo = kakaoService.getInfoByToken(token);
        return userInfo;
    }

    public Account findByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            throw new IllegalArgumentException();
        }
        return account;
    }
}
