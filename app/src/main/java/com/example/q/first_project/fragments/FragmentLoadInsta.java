package com.example.q.first_project.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.first_project.R;

public class FragmentLoadInsta extends Fragment{
    private View v;

    public FragmentLoadInsta() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.insta_loading, container, false);
        return v;
    }
}
