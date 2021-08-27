package com.kims.hackathon.view;

import static android.app.DatePickerDialog.*;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class TermView extends LinearLayout {

    private Context context;

    private SelectButtonView allowButton;
    private SelectButtonView denyButton;

    private ImageButton datePickerButton;
    private TextView dateTextView;

    private boolean isRequireDate = true;

    private int selectYear;
    private int selectMonth;
    private int selectDay;

    public TermView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public TermView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public LocalDate getDate() {
        return LocalDate.of(selectYear, selectMonth, selectDay);
    }

    public boolean isRequireDate() {
        return isRequireDate;
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.term_view, this);

        allowButton = findViewById(R.id.term_allow);
        allowButton.configure("allow", "추가", true);
        denyButton = findViewById(R.id.term_deny);
        denyButton.configure("deny", "추가안함", false);

        datePickerButton = findViewById(R.id.third_box_date_image);
        dateTextView = findViewById(R.id.third_box_date_text);

        allowButton.onClick(view -> {
            isRequireDate = true;
            datePickerButton.setEnabled(true);
            allowButton.setSelected(true);
            denyButton.setSelected(false);
            allowButton.changeTheme();
            denyButton.changeTheme();
        });
        denyButton.onClick(view -> {
            isRequireDate = false;
            datePickerButton.setEnabled(true);
            allowButton.setSelected(false);
            denyButton.setSelected(true);
            allowButton.changeTheme();
            denyButton.changeTheme();
        });
        datePickerButton.setOnClickListener(new DateButtonClickListener(this, context));
    }

    private void setDateText(int year, int month, int day) {
        String date = year + "년 " + month + "월 " + day + " 일";
        selectYear = year;
        selectMonth = month;
        selectDay = day;
        dateTextView.setText(date);
    }


    private static class DateButtonClickListener implements OnClickListener {
        private Context context;
        private TermView termView;

        DateButtonClickListener(TermView termView, Context context) {
            this.termView = termView;
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(context, new TermDateSetListener(termView), year, month, day).show();
        }
    }

    private static class TermDateSetListener implements OnDateSetListener {
        private TermView termView;

        TermDateSetListener(TermView termView) {
            this.termView = termView;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            termView.setDateText(year, month, day);
        }
    }

}
