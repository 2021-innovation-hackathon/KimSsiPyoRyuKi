package com.kims.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import com.kakao.sdk.user.UserApiClient;
import com.kims.hackathon.view.HocketProfileView;

public class HocketListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private HocketProfileView hocketProfileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hocket_list);
        setToolBar();
        setListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_white, menu);
        return true;
    }

    private void setListView() {
        hocketProfileView = findViewById(R.id.hocket_profile_view);
        UserApiClient.getInstance().me((user, throwable) -> {
            String nickname = user.getProperties().get("nickname");
            hocketProfileView.setNickname(nickname);
            return null;
        });
    }

    private void setToolBar() {
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}