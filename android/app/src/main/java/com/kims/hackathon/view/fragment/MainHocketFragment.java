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
import com.kims.hackathon.view.UnderlineTextView;

public class MainHocketFragment extends Fragment {

    private Context context;
    private UnderlineTextView underlineTextView;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.main_hocket_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu_purple, menu);
    }

    private void initView(View view) {
        this.underlineTextView = view.findViewById(R.id.main_text_view);
        this.toolbar = view.findViewById(R.id.main_toolbar);

        UserApiClient.getInstance().me((user, throwable) -> {
            String nickname = user.getProperties().get("nickname");
            underlineTextView.setText(nickname + "'s Hocket");
            return null;
        });
        setToolBar();
    }

    private void setToolBar() {
        MainActivity activity = (MainActivity)context;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
