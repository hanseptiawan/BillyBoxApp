package com.box.billy.billybox.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.AddCartItem;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;
import com.bumptech.glide.Glide;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends Fragment{

    TextView tv_nama, tv_cartonid, tv_category,
    tv_stock, tv_harga, tv_ukuran, tv_grametur;
    EditText et_jumlah;
    Button btn_addkeranjang;
    ImageView iv_img, iv_back;
    SessionManager sessionManager;
//    ApiServices apiServices;
    ApiServicesLokal apiServices;

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
        iv_back = view.findViewById(R.id.iv_back);
        et_jumlah = view.findViewById(R.id.et_jumlahpesan);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkAuthorization();
        apiServices = ApiUtils.getApiServices();

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
            tv_stock.setText(stock + " Bendel");
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

                    if (cartID != null){
                        addtoCart(id,jumlah,harga,cartid);
                    }
            }

        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Product product = new Product();
                fragmentTransaction.replace(R.id.fragment_container, product, "product");
                fragmentTransaction.addToBackStack("product");
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void addtoCart(String mid, String mjumlah, String mharga, String mcartid) {
        apiServices.addItem(mid, mjumlah, mharga, mid)
                .enqueue(new Callback<AddCartItem>() {
                    @Override
                    public void onResponse(Call<AddCartItem> call, Response<AddCartItem> response) {
                        Toast.makeText(getActivity(), "Berhasil menambahkan ke keranjang",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<AddCartItem> call, Throwable t) {
                        Toast.makeText(getActivity(), "Gagal menambahkan ke keranjang, " +t,
                                Toast.LENGTH_SHORT).show();
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
                btn_addkeranjang.setVisibility(View.VISIBLE);
            } else {
                btn_addkeranjang.setVisibility(View.INVISIBLE);
            }
        }

        return true;
    }
}
