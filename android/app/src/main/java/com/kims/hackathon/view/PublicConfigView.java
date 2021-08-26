package com.kims.hackathon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

public class PublicConfigView extends LinearLayout {

    private Context context;

    private SelectButtonView publicButton;
    private SelectButtonView privateButton;

    public PublicConfigView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public PublicConfigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.public_config_view, this);

        publicButton = findViewById(R.id.public_button);
        publicButton.configure("public", "공개", true);
        publicButton.onClick(view -> {
            publicButton.setSelected(true);
            privateButton.setSelected(false);
            publicButton.changeTheme();
            privateButton.changeTheme();
        });
        privateButton = findViewById(R.id.private_button);
        privateButton.configure("private", "비공개", false);
        privateButton.onClick(view -> {
            publicButton.setSelected(false);
            privateButton.setSelected(true);
            publicButton.changeTheme();
            privateButton.changeTheme();
        });
    }

}
