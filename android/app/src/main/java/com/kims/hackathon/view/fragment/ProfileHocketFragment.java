package com.kims.hackathon.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.kakao.sdk.user.UserApiClient;
import com.kims.hackathon.MainActivity;
import com.kims.hackathon.R;
import com.kims.hackathon.view.HocketProfileView;

public class ProfileHocketFragment extends Fragment {

    private Context context;
    private Toolbar toolbar;
    private HocketProfileView hocketProfileView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hocket_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu_white, menu);
    }

    private void initView(View view) {
        this.hocketProfileView = view.findViewById(R.id.hocket_profile_view);
        this.toolbar = view.findViewById(R.id.profile_toolbar);
        UserApiClient.getInstance().me((user, throwable) -> {
            String nickname = user.getProperties().get("nickname");
            hocketProfileView.setNickname(nickname);
            return null;
        });
        setToolBar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setToolBar() {
        MainActivity mainActivity = (MainActivity)context;
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black);
        mainActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
