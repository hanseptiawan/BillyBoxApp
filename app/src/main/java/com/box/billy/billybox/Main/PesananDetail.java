package com.box.billy.billybox.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.box.billy.billybox.R;

/**
 * Created by han on 7/13/2018.
 */

public class PesananDetail extends Fragment {

    Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailpesanan, container, false);


        return view;
    }
}
