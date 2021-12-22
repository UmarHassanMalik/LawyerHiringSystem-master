package com.innova.lawyerhiringsystem.getstarted;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innova.lawyerhiringsystem.R;

public class ScreenSlidePage extends Fragment {


    public ScreenSlidePage() {
        // Required empty public constructor
    }

    public static ScreenSlidePage newInstance(String param1, String param2) {
        ScreenSlidePage fragment = new ScreenSlidePage();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
    }
}