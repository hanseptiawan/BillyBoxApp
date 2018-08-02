package com.box.billy.billybox.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.CartAdapter;
import com.box.billy.billybox.Main.Order.OrderCheckout;
import com.box.billy.billybox.Model.GetCart;
import com.box.billy.billybox.Model.GetCartResponse;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Keranjang extends Fragment {

    public static final String ARG_ITEM_ID = "product_list";

    TextView tv_cartID, totalpayment;
    Button btn_checkOut;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager layoutManager;
    CartAdapter cartAdapter;
    ApiServices apiServices;
//    ApiServicesLokal apiServices;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        apiServices = ApiUtils.getApiServices();

        tv_cartID = view.findViewById(R.id.tv_cartid);
        btn_checkOut = view.findViewById(R.id.btn_checkout);
        recyclerView = view.findViewById(R.id.recycle_view_keranjang);
        totalpayment = view.findViewById(R.id.tv_totalpayment);

        HashMap<String, String> cartID = sessionManager.getCartID();
        final String cartid = cartID.get(sessionManager.KEY_CARTID);
        Log.d("cartid : ", cartid);
        if (cartid != null){
            tv_cartID.setText(cartid);
        } else {
            tv_cartID.setText(null);
        }

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(getContext());
        recyclerView.setAdapter(cartAdapter);

        getCartList(cartid);

        return view;
    }

    private void getCartList(final String cartid) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat data ...");
        progressDialog.show();
        if (cartid != null){
            apiServices.getCartList(cartid)
                    .enqueue(new Callback<GetCartResponse>() {
                        @Override
                        public void onResponse(Call<GetCartResponse> call, Response<GetCartResponse> response) {
                            progressDialog.hide();
                            List<GetCart> list = response.body().getDataBody();
                            if (list != null){
                                cartAdapter.setProductsList(list);

                                final int getTotal = response.body().getSubTotal();
                                totalpayment.setText("Rp." + String.valueOf(getTotal));

                                btn_checkOut.setVisibility(View.VISIBLE);

                                btn_checkOut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent a = new Intent(getActivity().getApplicationContext(), OrderCheckout.class);
                                        a.putExtra("cartid",cartid);
                                        a.putExtra("totalpayment", getTotal);
                                        startActivity(a);
                                    }
                                });
                            }else{
                                Toast.makeText(getActivity(), "Keranjang kosong, silahkan pesan terlebih dahulu",
                                        Toast.LENGTH_LONG).show();
                                btn_checkOut.setVisibility(View.GONE);
                            }


                        }

                        @Override
                        public void onFailure(Call<GetCartResponse> call, Throwable t) {
                            progressDialog.hide();
                            Toast.makeText(getActivity(), "Gagal memuat keranjang",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }else {
            Toast.makeText(getActivity(), "silahkan login terlebih dahulu",
                    Toast.LENGTH_LONG).show();
        }
    }
}
