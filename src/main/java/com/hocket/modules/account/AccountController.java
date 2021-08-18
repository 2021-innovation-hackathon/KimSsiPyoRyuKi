package com.hocket.modules.account;


import com.hocket.modules.account.dto.AccountDto;
import com.hocket.modules.account.form.LoginForm;
import com.hocket.modules.account.form.SignUpForm;
import com.hocket.modules.account.validator.AccountFormValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountFormValidator accountFormValidator;
    private final AccountService accountService;

    private final CacheManager cacheManager;

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;


    @InitBinder
    public void signUpInit(WebDataBinder webDataBinder ){
        webDataBinder.addValidators(accountFormValidator);
    }

    @PostMapping("/sign-up")
    public @ResponseBody String signUp(@Valid SignUpForm signUpForm, Errors errors) throws InterruptedException {
        if(errors.hasErrors()){
            return errors.getAllErrors().get(0).getCode();
        }
        Account newAccount = accountService.saveAccount(signUpForm);
        accountService.login(newAccount.getId(),signUpForm.getToken());

        return "ok";
    }

    @GetMapping("/account/info/{token}")
    public @ResponseBody AccountDto getAccountInfo(@PathVariable String token){
        Long accountId = (Long) cacheManager.getCache("account").get(token).get();
        Account account = accountRepository.findById(accountId).get();

        return modelMapper.map(account, AccountDto.class);

    }


}
