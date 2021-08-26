package com.hocket.modules.account;


import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.account.dto.AccountDto;
import com.hocket.modules.kakao.KakaoService;
import com.hocket.modules.kakao.dto.KakaoUserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    private final CacheManager cacheManager;

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final KakaoService kakaoService;


    @PostMapping("/sign-up")
    public ResponseEntity signUp(String token) {

        kakaoService.checkToken(token);
        KakaoUserInfoResponseDto userInfo = kakaoService.getInfoByToken(token);
        if(userInfo.getEmail() == null){
            log.info("null email");

            return ResponseEntity.badRequest().build();

        }
        if(userInfo.getNickname() == null){
            log.info("null nickname");

            return ResponseEntity.badRequest().build();

        }
        if(userInfo.getAge_range() == null){
            log.info("null age");

            return ResponseEntity.badRequest().build();
        }
        if(accountRepository.existsByEmail(userInfo.getEmail())){
            log.info("exists email");

            return ResponseEntity.badRequest().build();
        }
        Account newAccount = accountService.saveAccount(userInfo);
        accountService.login(newAccount.getId(),token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/info/{token}")
    public AccountDto getAccountInfo(@PathVariable String token){
        Cache.ValueWrapper valueWrapper = cacheManager.getCache("account").get(token);

        if(valueWrapper == null){
            return null;
        }

        Account account = accountRepository.findById((Long)valueWrapper.get()).get();

        return modelMapper.map(account, AccountDto.class);
    }

    @GetMapping("/account/check")
    public ResponseEntity isExistsAccount(String token){
        KakaoUserInfoResponseDto userInfo = kakaoService.getInfoByToken(token);
        if(!accountRepository.existsByEmail(userInfo.getEmail())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();

    }


}
