package com.kims.hackathon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.AuthApiClient;
import com.kakao.sdk.auth.TokenManager;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private Intent intent;
    private ImageButton button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        button = findViewById(R.id.login_btn);
        intent = new Intent(context, MainActivity.class);
        if (AuthApiClient.getInstance().hasToken()) {
            startActivity(intent);
        }
        button.setOnClickListener(this::onClickLoginButton);
    }

    public void onClickLoginButton(View v) {
        if (isKakaoTalkInstalled()) {
            UserApiClient.getInstance().loginWithKakaoTalk(context, loginCallBack(intent));
        }
        UserApiClient.getInstance().loginWithKakaoAccount(context, loginCallBack(intent));
    }

    private boolean isKakaoTalkInstalled() {
        return UserApiClient.getInstance().isKakaoTalkLoginAvailable(context);
    }

    private Function2<OAuthToken, Throwable, Unit> loginCallBack(Intent intent) {
        return (oAuthToken, throwable) -> {
            if (throwable != null) {
                Log.d("Error", throwable.getMessage());
            } else {
                startActivity(intent);
            }
            return null;
        };
    }
}
