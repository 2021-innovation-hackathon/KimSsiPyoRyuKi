package com.hocket.modules.account;

import com.hocket.modules.account.form.AccountForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;


    public Account saveAccount(AccountForm accountForm) {

        return accountRepository.save(modelMapper.map(accountForm, Account.class));

    }

    public void login(Account account){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                account.getNickname()
                ,null
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

    }
}
