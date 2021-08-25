package com.kims.hackathon.client.bucket;

import retrofit2.Call;
import retrofit2.http.POST;

interface HocketProxyService {

    @POST
    Call<String> createHocket();
}
