package com.kims.hackathon.client.account;

import com.kims.hackathon.client.RetrofitFactory;

import retrofit2.Call;

class AccountProxyServiceImpl implements AccountProxyService {
    private AccountProxyService accountService;

    public AccountProxyServiceImpl(String baseUrl) {
        this.accountService = RetrofitFactory
                .createRetrofit(baseUrl)
                .create(AccountProxyService.class);
    }

    @Override
    public Call<String> login(String token) {
        return accountService.login(token);
    }

    @Override
    public Call<String> logout(String token) {
        return accountService.logout(token);
    }

    @Override
    public Call<String> signUp(String token) {
        return accountService.signUp(token);
    }

    @Override
    public Call<Account> getAccountInfo(String token) {
        return accountService.getAccountInfo(token);
    }
}
