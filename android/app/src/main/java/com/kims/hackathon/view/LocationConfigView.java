package com.kims.hackathon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

public class LocationConfigView extends LinearLayout {
    private Context context;

    private SelectButtonView allowButton;
    private SelectButtonView denyButton;

    public LocationConfigView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public LocationConfigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.location_config_view, this);

        allowButton = findViewById(R.id.location_allow);
        allowButton.configure("Allow", "추가", true);
        allowButton.onClick(view -> {
            allowButton.setSelected(true);
            denyButton.setSelected(false);
            allowButton.changeTheme();
            denyButton.changeTheme();
        });
        denyButton = findViewById(R.id.location_deny);
        denyButton.configure("Deny", "추가안함", false);
        denyButton.onClick(view -> {
            allowButton.setSelected(false);
            denyButton.setSelected(true);
            allowButton.changeTheme();
            denyButton.changeTheme();
        });
    }
}
