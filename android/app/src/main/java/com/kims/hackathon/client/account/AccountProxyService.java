package com.kims.hackathon.client.account;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface AccountProxyService {
    @POST("/login")
    Call<String> login(@Query("token") String token);

    @POST("/logout")
    Call<String> logout(@Query("token") String token);

    @POST("/sign-up")
    Call<Void> signUp(@Query("token") String token);

    @GET("/account/info/{token}")
    Call<Account> getAccountInfo(@Query("token") String token);
}
