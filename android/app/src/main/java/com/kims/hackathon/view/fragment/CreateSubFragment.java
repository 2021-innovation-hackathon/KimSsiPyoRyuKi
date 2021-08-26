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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kims.hackathon.HocketCreateActivity;
import com.kims.hackathon.R;
import com.kims.hackathon.view.BackgroundConfigView;
import com.kims.hackathon.view.CategoryConfigView;
import com.kims.hackathon.view.LocationConfigView;

public class CreateSubFragment extends Fragment {

    private HocketCreateActivity hocketCreateActivity;

    private BackgroundConfigView backgroundConfigView;
    private CategoryConfigView categoryConfigView;
    private LocationConfigView locationConfigView;
    private FloatingActionButton createButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hocket_create_sub, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        this.createButton = view.findViewById(R.id.hocket_create_fab);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.hocketCreateActivity = (HocketCreateActivity) context;
    }
}
