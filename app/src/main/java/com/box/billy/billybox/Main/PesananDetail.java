package com.box.billy.billybox.Main;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.DataBodyPesanan;
import com.box.billy.billybox.Model.GetPesananDetail;
import com.box.billy.billybox.Model.GetPesananDetailResponse;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 7/13/2018.
 */

public class PesananDetail extends Fragment {

    ApiServices apiServices;
    TextView tv_orderid, tv_tglpesan,
                tv_totalbiaya, tv_ongkir, tv_total,
                tv_bayar, tv_tanggungan;
    Button btn_historyorder;
    ImageView iv_back;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailpesanan, container, false);

        apiServices = ApiUtils.getApiServices();

        tv_orderid = view.findViewById(R.id.tv_orderID);
        tv_tglpesan = view.findViewById(R.id.tv_tglpesan2);
        tv_totalbiaya = view.findViewById(R.id.tv_totalbiaya2);
        tv_ongkir = view.findViewById(R.id.tv_ongkir2);
        tv_total = view.findViewById(R.id.tv_total2);
        tv_bayar = view.findViewById(R.id.tv_bayar2);
        tv_tanggungan = view.findViewById(R.id.tv_tanggungan2);
        btn_historyorder = view.findViewById(R.id.btn_historyorder);
        recyclerView = view.findViewById(R.id.recycle_view_keranjang_pesanan);
        iv_back = view.findViewById(R.id.iv_back);

        if (getArguments() != null){
            String orderID = getArguments().getString("idpesanan");
            tv_orderid.setText(orderID);
            detailpesanan(orderID);
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Pesanan pesanan = new Pesanan();
                fragmentTransaction.replace(R.id.fragment_container, pesanan, "pesanan");
                fragmentTransaction.addToBackStack("pesanan");
                fragmentTransaction.commit();
            }
        });

        btn_historyorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(getActivity(), HistoryPesanan.class);
                String orderid = tv_orderid.getText().toString();
                Log.d("orderID : ", orderid);
                n.putExtra("orderid", orderid);
                startActivity(n);
            }
        });

        return view;
    }

    private void detailpesanan(String orderID) {
        apiServices.getPesananDetail(orderID)
                .enqueue(new Callback<GetPesananDetailResponse>() {
                    @Override
                    public void onResponse(Call<GetPesananDetailResponse> call, Response<GetPesananDetailResponse> response) {
                        if(response.body() != null){
                            DataBodyPesanan dataBodyPesanan = response.body().getDataBody();
                            GetPesananDetail getPesananDetail = dataBodyPesanan.get0();

                            tv_tglpesan.setText(getPesananDetail.getCreatedAt());
                            tv_totalbiaya.setText(getPesananDetail.getTotalHarga());
//                            tv_ongkir.setText(getPesananDetail.);
//                            tv_bayar.setText(getPesananDetail.);
                        }

                    }

                    @Override
                    public void onFailure(Call<GetPesananDetailResponse> call, Throwable t) {
                        Log.d("hqq :" , String.valueOf(t));
                        Toast.makeText(getActivity(), "Gagal memuat detail pesanan",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
