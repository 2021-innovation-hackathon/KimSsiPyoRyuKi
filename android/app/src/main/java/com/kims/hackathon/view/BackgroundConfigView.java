package com.kims.hackathon.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

public class BackgroundConfigView extends LinearLayout {

    private Context context;

    private TextView pictureText;
    private ImageButton pictureButton;
    private TextView colorText;
    private ImageButton colorButton;

    public BackgroundConfigView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public BackgroundConfigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.background_config_view, this);

        pictureButton = findViewById(R.id.first_box_image_view);
        pictureText = findViewById(R.id.first_box_text_view);
        colorButton = findViewById(R.id.second_box_image_view);
        colorText = findViewById(R.id.second_box_text_view);

        pictureButton.setOnClickListener(view -> {
            changeTheme(pictureButton, pictureText, true);
            changeTheme(colorButton, colorText,false);
        });
        colorButton.setOnClickListener(view -> {
            changeTheme(pictureButton, pictureText, false);
            changeTheme(colorButton ,colorText, true);
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeTheme(ImageButton imageButton, TextView textView, boolean selected) {
        if(selected) {
            imageButton.setBackground(getResources().getDrawable(R.drawable.box_image_border_solid, context.getTheme()));
            textView.setTextColor(getResources().getColor(R.color.white, context.getTheme()));
        } else {
            imageButton.setBackground(getResources().getDrawable(R.drawable.box_image_border, context.getTheme()));
            textView.setTextColor(getResources().getColor(R.color.text_gray_color, context.getTheme()));
        }
    }
}
