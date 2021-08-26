package com.kims.hackathon.client.bucket;

import retrofit2.Callback;

public class HocketService {

    private HocketProxyService hocketProxyService;

    public void createHocket(Hocket hocket, Callback<String> callback) {
        hocketProxyService.createHocket().enqueue(callback);
    }
}
