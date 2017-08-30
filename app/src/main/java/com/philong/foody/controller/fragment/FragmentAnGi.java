package com.philong.foody.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philong.foody.R;

/**
 * Created by Long on 8/30/2017.
 */

public class FragmentAnGi extends Fragment {

    public static FragmentAnGi newInstance() {
        Bundle args = new Bundle();
        FragmentAnGi fragment = new FragmentAnGi();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_an_gi, container, false);
        return view;
    }
}
