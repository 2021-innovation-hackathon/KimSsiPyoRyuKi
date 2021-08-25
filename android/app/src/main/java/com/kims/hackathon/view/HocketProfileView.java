package com.kims.hackathon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

public class HocketProfileView extends LinearLayout {

    private Context context;
    private TextView nicknameView;
    private TextView progressView;

    public HocketProfileView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public HocketProfileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.hocket_profile_view, this);

        nicknameView = findViewById(R.id.hocket_profile_nickname);
        progressView = findViewById(R.id.hocket_profile_progress);
    }

    public void setNickname(String nickname) {
        nickname += "'s Hocket";
        nicknameView.setText(nickname);
    }

    public void setProgress(int progress, int achieve) {
        String builder = progress + " Ing / " + achieve + " Achieve";
        progressView.setText(builder);
    }
}
