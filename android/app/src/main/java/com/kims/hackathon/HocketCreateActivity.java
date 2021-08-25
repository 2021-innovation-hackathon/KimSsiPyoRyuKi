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

import com.kims.hackathon.view.fragment.CreateMainFragment;
import com.kims.hackathon.view.fragment.CreateSubFragment;

import java.util.ArrayList;
import java.util.List;

public class HocketCreateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager2 hocketCreateViewPager;
    private static int HOME = 16908332;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hocket_create);
        initView();
        setViewPager();
        setToolBar();
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
        adapter.addFragment(new CreateMainFragment());
        adapter.addFragment(new CreateSubFragment());
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
