package com.hocket.modules.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.hocket.modules.account.Account;
import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class MainController {

    private final AccountRepository accountRepository;
    private final AccountService accountService;



    @PostMapping("/login")
    public ResponseEntity login(String token){
        String email="";
        boolean isValid = accountService.checkToken(token);

        if(isValid){
            JsonNode userInfo = accountService.getInfoByToken(token);

            if(userInfo.findValue("email") ==null){
                return ResponseEntity.badRequest().build();
            }
            email = userInfo.findValue("email").textValue();
        }
        if(!isValid){
            return ResponseEntity.badRequest().build();
        }

        Account account = accountRepository.findByEmail(email);

        if(account == null){
            return ResponseEntity.notFound().build();
        }
        else{
            accountService.login(account.getId(), token);
            return ResponseEntity.ok().build();
        }
    }
    @PostMapping("/logout")
    public ResponseEntity logout(String token){
        accountService.logout(token);

        return ResponseEntity.ok().build();
    }



}
