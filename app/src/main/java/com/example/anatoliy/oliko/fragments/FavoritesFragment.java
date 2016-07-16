package com.example.anatoliy.oliko.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anatoliy.oliko.R;

/**
 * Created by anatoliy on 20/02/16.
 */
public class FavoritesFragment extends BaseFragment {

    public static final String TAG = FavoritesFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorites_layout, container, false);
        initUi(view);
        return view;
    }

    private void initUi(View view) {

    }
}
