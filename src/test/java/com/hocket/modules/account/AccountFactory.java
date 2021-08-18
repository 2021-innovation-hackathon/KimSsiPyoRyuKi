package com.hocket.modules.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {

    @Autowired
    AccountRepository accountRepository;

    public Account createNewAccount(String nickname, String email){

        Account account = new Account();
        account.setNickname(nickname);
        account.setName("김태준");
        account.setEmail(email);
        account.setProfileImage("profileImage");
        account.setAgeRange("20~29");
        account.setName("김태준");


        return accountRepository.save(account);
    }
}
