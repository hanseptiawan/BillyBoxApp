package com.box.billy.billybox.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.box.billy.billybox.Model.GetProduct;
import com.box.billy.billybox.Utils.SharedPreference;
import com.box.billy.billybox.R;

import java.util.List;

public class Keranjang extends Fragment {

    public static final String ARG_ITEM_ID = "product_list";

    Activity activity;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<GetProduct> products;
    SharedPreference sharedPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        sharedPreference = new SharedPreference();
        products = sharedPreference.getFavorite(activity);

        if (products == null){
            showAlert(getResources().getString(R.string.no_fav),
                    getResources().getString(R.string.no_fav_msg));
        } else {
            if (products.size() == 0){
                showAlert(
                        getResources().getString(R.string.no_fav),
                        getResources().getString(R.string.no_fav_msg));
            }
        }

        return view;
    }

    public void showAlert(String title, String message){
        if (activity != null && !activity.isFinishing()){
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }
}
