package com.kims.hackathon.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kims.hackathon.R;
import com.kims.hackathon.view.recyclerview.CategoryHocketRecyclerView;

public class CategoryFragment extends Fragment {

    private Category category;
    private CategoryHocketRecyclerView categoryHocketRecyclerView;

    public CategoryFragment(Category category) {
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_hocket_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryHocketRecyclerView = view.findViewById(R.id.category_hocket_recycler_view);
    }

    public enum Category {
        POPULAR,
        EXERCISE,
        HOBBY,
        LEARNING
    }
}
