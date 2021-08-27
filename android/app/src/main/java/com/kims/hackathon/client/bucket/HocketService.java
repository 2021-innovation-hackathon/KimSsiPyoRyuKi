package com.kims.hackathon.client.bucket;

import retrofit2.Callback;

public class HocketService {

    private HocketProxyService hocketProxyService;

    public HocketService(String baseUrl) {
        this.hocketProxyService = new HocketProxyServiceImpl(baseUrl);
    }

    public void createHocket(String token, Hocket hocket, Callback<Void> callback) {
        hocketProxyService.createHocket(token, hocket).enqueue(callback);
    }
}
