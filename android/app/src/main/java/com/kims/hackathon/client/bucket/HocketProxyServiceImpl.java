package com.kims.hackathon.client.bucket;

import com.kims.hackathon.client.RetrofitFactory;

import retrofit2.Call;

class HocketProxyServiceImpl implements HocketProxyService {

    private final HocketProxyService hocketProxyService;

    public HocketProxyServiceImpl(String baseUrl) {
        this.hocketProxyService = RetrofitFactory
                .createRetrofit(baseUrl)
                .create(HocketProxyService.class);
    }

    @Override
    public Call<Void> createHocket(String token, Hocket hocket) {
        return hocketProxyService.createHocket(token, hocket);
    }
}
