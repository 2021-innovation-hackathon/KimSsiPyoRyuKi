package com.kims.hackathon.view;

import static com.google.android.material.tabs.TabLayoutMediator.*;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kims.hackathon.HocketCreateActivity;
import com.kims.hackathon.MainActivity;
import com.kims.hackathon.R;
import com.kims.hackathon.view.fragment.CategoryFragment;
import com.kims.hackathon.view.fragment.MainHocketFragment;
import com.kims.hackathon.view.fragment.ProfileHocketFragment;

import java.util.ArrayList;
import java.util.List;

public class MainTabView extends LinearLayout {

    private Context context;

    private TabLayout mainTabLayout;
    private ViewPager2 mainViewPager;
    private FloatingActionButton fab;

    public MainTabView(Context context) {
        super(context);
        this.context = context;
        initView();
        initTabLayout();
    }

    public MainTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        initTabLayout();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.main_tab_view, this);

        mainTabLayout = findViewById(R.id.main_tab_layout);
        mainViewPager = findViewById(R.id.main_tab_viewpager);
        fab = findViewById(R.id.main_tab_fab);

        FragmentActivity fragmentActivity = (FragmentActivity)context;

        MainTabLayoutFragmentAdapter mainTabLayoutFragmentAdapter
                = new MainTabLayoutFragmentAdapter(fragmentActivity);

        mainTabLayoutFragmentAdapter.addFragment(new MainHocketFragment());
        mainTabLayoutFragmentAdapter.addFragment(new ProfileHocketFragment());

        mainViewPager.setUserInputEnabled(false);
        mainViewPager.setAdapter(mainTabLayoutFragmentAdapter);

        fab.setOnClickListener(new FabClickListener(context));
    }

    private void initTabLayout() {
        new TabLayoutMediator(mainTabLayout, mainViewPager, new MainTabConfigStrategy())
                .attach();
    }

    private static class MainTabConfigStrategy implements TabConfigurationStrategy {
        private static final int MAIN = 0;
        private static final int PROFILE = 1;

        @Override
        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            switch (position) {
                case (MAIN):
                    tab.setIcon(R.drawable.ic_house_black_silhouette_without_door);
                    break;
                case (PROFILE):
                    tab.setIcon(R.drawable.ic_user);
                    break;
            }
        }
    }

    private static class MainTabLayoutFragmentAdapter extends FragmentStateAdapter {

        private List<Fragment> fragments;

        public MainTabLayoutFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            this.fragments = new ArrayList<>();
        }

        public MainTabLayoutFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            this.fragments = new ArrayList<>();
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

        public void addFragment(Fragment fragment) {
            this.fragments.add(fragment);
        }
    }

    private static class FabClickListener implements OnClickListener {

        private Context context;

        FabClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            MainActivity mainActivity = (MainActivity)context;
            Intent intent = new Intent(mainActivity, HocketCreateActivity.class);
            mainActivity.startActivity(intent);
        }
    }
}
