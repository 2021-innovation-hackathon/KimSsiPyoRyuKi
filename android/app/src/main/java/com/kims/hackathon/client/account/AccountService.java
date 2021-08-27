package com.kims.hackathon.client.account;

import retrofit2.Callback;

public class AccountService {

    private AccountProxyService proxyService;

    public AccountService(String baseUrl) {
        this.proxyService = new AccountProxyServiceImpl(baseUrl);
    }

    public void login(String token, Callback<String> callback) {
        proxyService.login(token).enqueue(callback);
    }

    public void logout(String token, Callback<String> callback) {
        proxyService.logout(token).enqueue(callback);
    }

    public void signUp(String token, Callback<Void> callback) {
        proxyService.signUp(token).enqueue(callback);
    }

    public void getAccountInfo(String token, Callback<Account> callback) {
        proxyService.getAccountInfo(token).enqueue(callback);
    }
}
