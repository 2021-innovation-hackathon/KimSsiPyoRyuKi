package com.hocket.modules.account;


import com.hocket.modules.account.form.AccountForm;
import com.hocket.modules.account.form.AccountFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountFormValidator accountFormValidator;
    private final AccountService accountService;

    @InitBinder
    public void signUpInit(WebDataBinder webDataBinder ){
        webDataBinder.addValidators(accountFormValidator);
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid AccountForm accountForm, Errors errors){
        if(errors.hasErrors()){
            return errors.getAllErrors().get(0).getCode();
        }
        Account newAccount = accountService.saveAccount(accountForm);
        accountService.login(newAccount);

        return "ok";

    }


}
