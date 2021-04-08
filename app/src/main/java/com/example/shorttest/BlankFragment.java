package com.example.shorttest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;


public class BlankFragment extends Fragment {


    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        tv = view.findViewById(R.id.tv);

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            tv.setText(title);
        }

        return view;
    }

    public void setText(String text) {
        if (tv == null) {
            return;
        }
        tv.setText(text);
    }
}