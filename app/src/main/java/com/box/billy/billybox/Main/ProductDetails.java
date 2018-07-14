package com.box.billy.billybox.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.SessionManager;
import com.box.billy.billybox.R;
import com.bumptech.glide.Glide;

public class ProductDetails extends Fragment{

    TextView tv_nama, tv_cartonid, tv_category,
    tv_stock, tv_harga, tv_ukuran, tv_grametur;
    Button btn_addkeranjang;
    ImageView iv_img;
    SessionManager sessionManager;
    Glide glide;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailproducts, container, false);

        tv_nama = view.findViewById(R.id.tv_nama);
        tv_cartonid = view.findViewById(R.id.tv_cartonid2);
        tv_category = view.findViewById(R.id.tv_category2);
        tv_stock = view.findViewById(R.id.tv_stock2);
        tv_harga = view.findViewById(R.id.tv_harga2);
        tv_ukuran = view.findViewById(R.id.tv_ukuran2);
        tv_grametur = view.findViewById(R.id.tv_grametur2);
        btn_addkeranjang = view.findViewById(R.id.btn_addkeranjang);
        iv_img = view.findViewById(R.id.iv_product);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkAuthorization();

        if(getArguments() != null){
            String cartonID = getArguments().getString("cartonID");
            String namaItem = getArguments().getString("namaItem");
            String catID = getArguments().getString("catID");
            String stock = getArguments().getString("stock");
            String harga = getArguments().getString("harga");
            String ukuran = getArguments().getString("ukuran");
            String grametur = getArguments().getString("grametur");
            String img = getArguments().getString("img");
            Log.d("imgurl : ", img);
            String namacat = getArguments().getString("namacat");

            tv_nama.setText(namaItem);
            tv_cartonid.setText(cartonID);
            tv_category.setText(namacat);
            tv_stock.setText(stock);
            tv_harga.setText(harga);
            tv_ukuran.setText(ukuran);
            tv_grametur.setText(grametur);

            Glide.with(getContext())
                    .load(img)
                    .fitCenter()
                    .placeholder(R.drawable.ic_noimg)
                    .error(R.drawable.ic_broken_image)
                    .into(iv_img);
        }

        checkuser(tv_nama.getText().toString());

        btn_addkeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments() != null){
                    tv_nama.getText().toString();

                }
            }
        });

        return view;
    }

    private boolean checkuser(String nama) {
        if(nama == null || nama.trim().length() == 0){

            Toast.makeText(getActivity(), "Silahkan login terlebih dahulu untuk order",
                    Toast.LENGTH_LONG).show();

            return false;
        }
        else {
            if (!sessionManager.checkAuthorization()) {
                btn_addkeranjang.setVisibility(View.VISIBLE);
            } else {
                btn_addkeranjang.setVisibility(View.INVISIBLE);
            }
        }

        return true;
    }
}
