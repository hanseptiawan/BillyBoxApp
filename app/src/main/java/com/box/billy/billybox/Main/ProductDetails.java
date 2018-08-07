package com.box.billy.billybox.Main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.AddCartItem;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;
import com.bumptech.glide.Glide;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProductDetails extends Fragment{

    TextView tv_nama, tv_cartonid, tv_category,
    tv_stock, tv_harga, tv_ukuran, tv_grametur;
    EditText et_jumlah;
    Button btn_addkeranjang;
    ImageView iv_img, iv_back;
    SessionManager sessionManager;
    ApiServices apiServices;
//    ApiServicesLokal apiServices;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailproducts, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tv_nama = view.findViewById(R.id.tv_nama);
        tv_cartonid = view.findViewById(R.id.tv_cartonid2);
        tv_category = view.findViewById(R.id.tv_category2);
        tv_stock = view.findViewById(R.id.tv_stock2);
        tv_harga = view.findViewById(R.id.tv_harga2);
        tv_ukuran = view.findViewById(R.id.tv_ukuran2);
        tv_grametur = view.findViewById(R.id.tv_grametur2);
        btn_addkeranjang = view.findViewById(R.id.btn_addkeranjang);
        iv_img = view.findViewById(R.id.iv_product);
        iv_back = view.findViewById(R.id.iv_back);
        et_jumlah = view.findViewById(R.id.et_jumlahpesan);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkAuthorization();
        apiServices = ApiUtils.getApiServices();

        HashMap<String, String> cartID = sessionManager.getCartID();
        String cartid = cartID.get(sessionManager.KEY_CARTID);

        if(getArguments() != null){
            String cartonID = getArguments().getString("cartonId");
            String namaItem = getArguments().getString("namaItem");
            String catID = getArguments().getString("catID");
            String stock = getArguments().getString("stock");
            String harga = getArguments().getString("harga");
            String ukuran = getArguments().getString("ukuran");
            String grametur = getArguments().getString("grametur");
            String img = getArguments().getString("img");
            Log.d(TAG, "onCreateView: " + img);
            String namacat = getArguments().getString("namacat");

            tv_nama.setText(namaItem);
            tv_cartonid.setText(cartonID);
            tv_category.setText(namacat);
            if (Integer.valueOf(stock) <= 0){
                tv_stock.setText("Stock Kosong");
                et_jumlah.setEnabled(false);
                btn_addkeranjang.setEnabled(false);
                btn_addkeranjang.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_disabled));
            }else {
                tv_stock.setText(stock + " Bendel");
            }
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
                HashMap<String, String> cartID = sessionManager.getCartID();
                String cartid = cartID.get(sessionManager.KEY_CARTID);

                String id = tv_cartonid.getText().toString();
                String harga = tv_harga.getText().toString();
                String jumlah = et_jumlah.getText().toString();
                Log.d("cartid : ", cartid);
//                    if (cartID != null){
                if (et_jumlah.length() == 0){
                    Toast.makeText(getActivity(), "Silahkan masukkan jumlah karton yang akan dibeli",
                            Toast.LENGTH_SHORT).show();
                    et_jumlah.requestFocus();
                }else {
                    addtoCart(id,jumlah,harga,cartid);
                }
//                    }
            }

        });

        final String catID = getArguments().getString("catID");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Product product = new Product();
                Bundle bundle = new Bundle();
                bundle.putString("catID", catID);

                product.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, product, "product");
                fragmentTransaction.addToBackStack("product");
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void addtoCart(String mid, String mjumlah, String mharga, String mcartid) {
        Log.d("parameter order : ", mid+" "+mjumlah+" "+mharga+" "+mcartid);
        apiServices.addItem(mid, mjumlah, mharga, mcartid)
                .enqueue(new Callback<AddCartItem>() {
                    @Override
                    public void onResponse(Call<AddCartItem> call, Response<AddCartItem> response) {
                        Log.d("response : ", String.valueOf(response));
                        Toast.makeText(getActivity(), "Berhasil menambahkan ke keranjang",
                                Toast.LENGTH_SHORT).show();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        ProductCategory product = new ProductCategory();
                        Bundle bundle = new Bundle();

                        product.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, product, "product");
                        fragmentTransaction.addToBackStack("product");
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onFailure(Call<AddCartItem> call, Throwable t) {
                        Log.d("response failure : ", String.valueOf(t));
                        Toast.makeText(getActivity(), "Berhasil menambahkan ke keranjang ",
                                Toast.LENGTH_SHORT).show();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        ProductCategory product = new ProductCategory();
                        Bundle bundle = new Bundle();

                        product.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, product, "product");
                        fragmentTransaction.addToBackStack("product");
                        fragmentTransaction.commit();
                    }
                });
    }

    private boolean checkuser(String nama) {
        if(nama == null || nama.trim().length() == 0){

            Toast.makeText(getActivity(), "Silahkan login terlebih dahulu untuk order",
                    Toast.LENGTH_LONG).show();

            return false;
        }
        else {
            if (!sessionManager.checkAuthorization()) {
                et_jumlah.setVisibility(View.VISIBLE);
                btn_addkeranjang.setVisibility(View.VISIBLE);
            } else {
                et_jumlah.setVisibility(View.INVISIBLE);
                btn_addkeranjang.setVisibility(View.INVISIBLE);
            }
        }

        return true;
    }
}
