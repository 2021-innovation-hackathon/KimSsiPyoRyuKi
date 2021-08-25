package com.kims.hackathon;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY));
    }

    public static GlobalApplication getInstance() {
        return instance;
    }

}
