package com.hocket.modules.account;


import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.account.dto.AccountDto;
import com.hocket.modules.account.form.SignUpForm;
import com.hocket.modules.account.validator.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;

    private final CacheManager cacheManager;

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @InitBinder("signUpForm")
    public void signUpInit(WebDataBinder webDataBinder ){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid SignUpForm signUpForm, Errors errors) {

        if(errors.hasErrors()){
            return errors.getAllErrors().get(0).getCode();
        }
        String token = signUpForm.getToken();
        boolean isValid = accountService.checkToken(token);

        if(isValid){
            JsonNode userInfo = accountService.getInfoByToken(token);
            if(userInfo.findValue("email") ==null){
                return "disagree.email";
            }
            if(accountRepository.existsByEmail(userInfo.findValue("email").textValue())){
                return "exists.email";
            }
            Account newAccount = accountService.saveAccount(userInfo, signUpForm.getNickname());
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
