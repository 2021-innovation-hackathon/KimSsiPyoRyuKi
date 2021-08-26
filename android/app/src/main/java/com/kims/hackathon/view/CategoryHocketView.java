package com.kims.hackathon.view;

import static com.google.android.material.tabs.TabLayoutMediator.*;
import static com.kims.hackathon.view.fragment.CategoryFragment.*;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kims.hackathon.R;
import com.kims.hackathon.view.fragment.CategoryFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryHocketView extends LinearLayout {

    private Context context;

    private TabLayout categoryTabLayout;
    private ViewPager2 categoryViewPager;
    private CategoryTabLayoutFragmentAdapter tabLayoutFragmentAdapter;

    public CategoryHocketView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CategoryHocketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        initTabLayout();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.category_hocket_view, this);

        categoryTabLayout = findViewById(R.id.category_tab_layout);
        categoryViewPager = findViewById(R.id.category_tab_view_pager);

        FragmentActivity activity = (FragmentActivity)context;
        tabLayoutFragmentAdapter = new CategoryTabLayoutFragmentAdapter(activity);
        tabLayoutFragmentAdapter.addFragment(new CategoryFragment(Category.HOBBY));
        tabLayoutFragmentAdapter.addFragment(new CategoryFragment(Category.EXERCISE));
        tabLayoutFragmentAdapter.addFragment(new CategoryFragment(Category.LEARNING));
        tabLayoutFragmentAdapter.addFragment(new CategoryFragment(Category.POPULAR));

        categoryViewPager.setUserInputEnabled(false);
        categoryViewPager.setAdapter(tabLayoutFragmentAdapter);
    }

    private void initTabLayout() {
        new TabLayoutMediator(categoryTabLayout, categoryViewPager, new CategoryTabConfigStrategy())
                .attach();
    }

    static class CategoryTabConfigStrategy implements TabConfigurationStrategy {

        private static final int POPULAR = 0;
        private static final int EXERCISE = 1;
        private static final int HOBBY = 2;
        private static final int LEARNING = 3;

        @Override
        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            switch (position) {
                case (POPULAR):
                    tab.setText("Popular");
                    break;
                case (EXERCISE):
                    tab.setText("Exercise");
                    break;
                case (HOBBY):
                    tab.setText("Hobby");
                    break;
                case (LEARNING):
                    tab.setText("Learning");
                    break;
            }
        }
    }

    static class CategoryTabLayoutFragmentAdapter extends FragmentStateAdapter {

        private List<Fragment> fragments;

        public CategoryTabLayoutFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            this.fragments = new ArrayList<>();
        }

        public CategoryTabLayoutFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            this.fragments = new ArrayList<>();
        }


        public void addFragment(Fragment fragment) {
            this.fragments.add(fragment);
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
