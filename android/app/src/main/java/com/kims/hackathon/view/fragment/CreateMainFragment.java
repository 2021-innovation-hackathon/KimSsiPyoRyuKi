package com.kims.hackathon.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kims.hackathon.HocketCreateActivity;
import com.kims.hackathon.R;
import com.kims.hackathon.client.bucket.Hocket;
import com.kims.hackathon.view.TermView;

public class CreateMainFragment extends Fragment {

    private HocketCreateActivity hocketCreateActivity;
    private FloatingActionButton fab;

    private EditText titleView;
    private EditText descView;
    private TermView termView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.hocket_create_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        fab = view.findViewById(R.id.hocket_create_fab);
        titleView = view.findViewById(R.id.hocket_create_input_title);
        descView = view.findViewById(R.id.hocket_create_input_desc);
        termView = view.findViewById(R.id.term_view);
        fab.setOnClickListener(fab -> hocketCreateActivity.changeNextFragment());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.hocketCreateActivity = (HocketCreateActivity)context;
    }

    public void setHocketInfo(Hocket hocket) {
        hocket.setTitle(titleView.getText().toString());
        hocket.setDescription(descView.getText().toString());
        hocket.setEndDate(termView.getDate());
        hocket.setRequireDate(termView.isRequireDate());
    }
}
