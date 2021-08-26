package com.kims.hackathon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

public class SelectButtonView extends RelativeLayout {

    private Context context;

    private ImageButton imageButton;
    private TextView textView;

    private String title;
    private boolean isSelected;

    public SelectButtonView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SelectButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.select_button_view, this);
        this.textView = findViewById(R.id.select_text);
        this.imageButton = findViewById(R.id.select_image_button);
        this.imageButton.setOnClickListener(new ImageButtonClickListener(this));
    }

    public void configure(String title, String text, boolean isSelected) {
        this.title = title;
        this.textView.setText(text);
        this.isSelected = isSelected;
        changeTheme();
    }

    public void changeTheme() {
        if(isSelected) {
            imageButton.setBackground(getResources().getDrawable(R.drawable.box_image_border_solid, context.getTheme()));
            textView.setTextColor(getResources().getColor(R.color.white, context.getTheme()));
        } else {
            imageButton.setBackground(getResources().getDrawable(R.drawable.box_image_border, context.getTheme()));
            textView.setTextColor(getResources().getColor(R.color.text_gray_color, context.getTheme()));
        }
    }

    public void toggle() {
        if(isSelected) {
            isSelected = false;
        } else {
            isSelected = true;
        }
    }

    public void onClick(OnClickListener listener) {
        this.imageButton.setOnClickListener(listener);
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private static class ImageButtonClickListener implements OnClickListener {

        private SelectButtonView selectButtonView;

        ImageButtonClickListener(SelectButtonView selectButtonView) {
            this.selectButtonView = selectButtonView;
        }

        @Override
        public void onClick(View view) {
            selectButtonView.toggle();
            selectButtonView.changeTheme();
        }
    }
}
