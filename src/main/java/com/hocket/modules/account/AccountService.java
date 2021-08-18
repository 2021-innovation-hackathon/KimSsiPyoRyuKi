package com.hocket.modules.account;

import com.hocket.modules.account.form.LoginForm;
import com.hocket.modules.account.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

//    private final EhCacheCacheManager ehCacheCacheManager;
    private final CacheManager cacheManager;


    public Account saveAccount(SignUpForm signUpForm) {

        Account account = accountRepository.save(modelMapper.map(signUpForm , Account.class));

        return account;
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
}
