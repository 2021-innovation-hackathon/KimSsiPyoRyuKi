package com.hocket.modules.main;

import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import com.hocket.modules.account.form.AccountForm;
import com.hocket.modules.account.form.AccountFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class Main {

    private final AccountRepository accountRepository;
    private final AccountService accountService;



    @PostMapping("/login")
    public ResponseEntity login(AccountForm accountForm){
        Account account = accountRepository.findByEmail(accountForm.getEmail());
        //TODO 토큰 관련.

        if(account == null){
            return ResponseEntity.notFound().build();
        }
        else{
            accountService.login(account);
            return ResponseEntity.ok().build();
        }
    }


}
