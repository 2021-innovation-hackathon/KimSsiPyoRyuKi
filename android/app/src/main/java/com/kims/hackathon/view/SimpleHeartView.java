package com.kims.hackathon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

public class SimpleHeartView extends LinearLayout {

    private Context context;
    private TextView textView;

    public SimpleHeartView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SimpleHeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public void setLikeNumber(int likes) {
        this.textView.setText(String.valueOf(likes));
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.simple_like_view, this);
        textView = findViewById(R.id.simple_like_num);
    }
}
