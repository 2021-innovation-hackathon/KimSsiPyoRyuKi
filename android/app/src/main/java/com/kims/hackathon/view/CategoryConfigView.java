package com.kims.hackathon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;
import com.kims.hackathon.view.fragment.CategoryFragment;
import com.kims.hackathon.view.fragment.CategoryFragment.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryConfigView extends LinearLayout {

    private Context context;

    private List<SelectButtonView> selectViews;

    private SelectButtonView homeButton;
    private SelectButtonView challengeButton;
    private SelectButtonView hobbyButton;
    private SelectButtonView exerciseButton;
    private SelectButtonView learningButton;
    private SelectButtonView foodButton;
    private SelectButtonView cultureButton;
    private SelectButtonView travelButton;
    private SelectButtonView etcButton;


    public CategoryConfigView(Context context) {
        super(context);
        this.context = context;
        this.selectViews = new ArrayList<>();
        initView();
    }

    public CategoryConfigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.selectViews = new ArrayList<>();
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.category_config_view, this);
        homeButton = findViewById(R.id.category_home);
        homeButton.configure("home", "Home", false);
        selectViews.add(homeButton);
        challengeButton = findViewById(R.id.category_challenge);
        challengeButton.configure("challenge", "Challenge", false);
        selectViews.add(challengeButton);
        hobbyButton = findViewById(R.id.category_hobby);
        hobbyButton.configure("hobby", "Hobby", false);
        selectViews.add(hobbyButton);
        exerciseButton = findViewById(R.id.category_exercise);
        exerciseButton.configure("exercise", "Exercise", false);
        selectViews.add(exerciseButton);
        learningButton = findViewById(R.id.category_learning);
        learningButton.configure("learning", "Learning", false);
        selectViews.add(learningButton);
        foodButton = findViewById(R.id.category_food);
        foodButton.configure("food", "Food", false);
        selectViews.add(foodButton);
        cultureButton = findViewById(R.id.category_culture);
        cultureButton.configure("culture", "Culture", false);
        selectViews.add(cultureButton);
        travelButton = findViewById(R.id.category_travel);
        travelButton.configure("travel", "Travel", false);
        selectViews.add(travelButton);
        etcButton = findViewById(R.id.category_etc);
        etcButton.configure("etc", "Etc", false);
        selectViews.add(etcButton);
    }

    public List<String> getSelectedCategory() {
        return selectViews.stream().filter(SelectButtonView::isSelected)
                .map(SelectButtonView::getTitle)
                .collect(Collectors.toList());
    }
}
