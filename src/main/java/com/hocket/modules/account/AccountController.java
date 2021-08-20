package com.hocket.modules.account;


import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    private final CacheManager cacheManager;

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;


    @PostMapping("/sign-up")
    public String signUp(String token) {

        boolean isValid = accountService.checkToken(token);

        if(isValid){
            JsonNode userInfo = accountService.getInfoByToken(token);
            if(userInfo.findValue("email") == null){
                return "disagree.email";
            }
            if(userInfo.findValue("nickname") == null){
                return "disagree.nickname";
            }
            if(userInfo.findValue("age_range") == null){
                return "disagree.ageRange";
            }
            if(accountRepository.existsByEmail(userInfo.findValue("email").textValue())){
                return "exists.email";
            }
            Account newAccount = accountService.saveAccount(userInfo);
            accountService.login(newAccount.getId(),token);
        }
        if(!isValid){
            return "wrong.token";
        }

        return "ok";
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


}
