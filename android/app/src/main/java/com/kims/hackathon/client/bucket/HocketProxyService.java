package com.kims.hackathon.client.bucket;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface HocketProxyService {

    @POST("/hocket/create")
    Call<Void> createHocket(@Query("token")String token, @Body Hocket hocket);
}
