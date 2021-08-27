package com.kims.hackathon;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.kakao.sdk.auth.TokenManager;
import com.kims.hackathon.client.bucket.Hocket;
import com.kims.hackathon.client.bucket.HocketService;
import com.kims.hackathon.view.fragment.CreateMainFragment;
import com.kims.hackathon.view.fragment.CreateSubFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HocketCreateActivity extends AppCompatActivity {

    private CreateMainFragment mainFragment;
    private CreateSubFragment subFragment;

    private HocketService hocketService;

    private Toolbar toolbar;
    private ViewPager2 hocketCreateViewPager;

    private static int HOME = 16908332;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hocket_create);
        hocketService = new HocketService("https://hocket-server.herokuapp.com");
        initView();
        setViewPager();
        setToolBar();
    }

    public void createHocket() {
        String token = TokenManager.getInstance().getToken().getAccessToken();
        Hocket hocket = new Hocket();
        mainFragment.setHocketInfo(hocket);
        subFragment.setHocketInfo(hocket);
        hocketService.createHocket(token, hocket, new Callback<Void>(){

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void changeNextFragment() {
        int current = hocketCreateViewPager.getCurrentItem();
        this.hocketCreateViewPager.setCurrentItem(current + 1);
    }

    private void initView() {
        hocketCreateViewPager = findViewById(R.id.hocket_create_viewpager);
        toolbar = findViewById(R.id.hocket_create_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_hocket_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setViewPager() {
        HocketCreatePagerAdapter adapter = new HocketCreatePagerAdapter(this);
        mainFragment = new CreateMainFragment();
        subFragment = new CreateSubFragment();
        adapter.addFragment(mainFragment);
        adapter.addFragment(subFragment);
        hocketCreateViewPager.setAdapter(adapter);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == HOME){
            finish();
        }
        return true;
    }

    private static class HocketCreatePagerAdapter extends FragmentStateAdapter {

        private List<Fragment> fragments;

        public HocketCreatePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            this.fragments = new ArrayList<>();
        }

        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }
}
