package com.box.billy.billybox.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.CartPesananAdapter;
import com.box.billy.billybox.Model.DataBodyPesanan;
import com.box.billy.billybox.Model.GetKeranjangPesanan;
import com.box.billy.billybox.Model.GetKeranjangPesananResponse;
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
//    ApiServicesLokal apiServices;
    TextView tv_orderid, tv_tglpesan,
        tvjenisbayar, tvjeniskirim, tvtglantar,
        tvtglterima, tvalamat, tvkota, tvtelp;
    Button btn_ceknota;
    ImageView iv_back;
    CartPesananAdapter cartAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailpesanan, container, false);

        apiServices = ApiUtils.getApiServices();

        tv_orderid = view.findViewById(R.id.tv_orderID);
        tv_tglpesan = view.findViewById(R.id.tv_tglpesan2);
        tvjenisbayar = view.findViewById(R.id.tv_jenisbayar);
        tvjeniskirim = view.findViewById(R.id.tv_jeniskirim);
        tvtglantar = view.findViewById(R.id.tv_tglantar);
        tvtglterima = view.findViewById(R.id.tv_tglterima);
        tvalamat = view.findViewById(R.id.tv_alamat);
        tvkota = view.findViewById(R.id.tv_kota2);
        tvtelp = view.findViewById(R.id.tv_telp2);
        btn_ceknota = view.findViewById(R.id.btn_ceknota);
        recyclerView = view.findViewById(R.id.recycle_view_keranjang_pesanan);
        iv_back = view.findViewById(R.id.iv_back);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartPesananAdapter(getContext());
        recyclerView.setAdapter(cartAdapter);

        if (getArguments() != null){
            String orderID = getArguments().getString("idpesanan");
            Log.d("orderID pesanan : ", orderID);
            tv_orderid.setText(orderID);

//            getKeranjangList(orderID);
//            detailpesanan(orderID);
        }

        String a = tv_orderid.getText().toString();

        getKeranjangList(a);
        detailpesanan(a);
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

        btn_ceknota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(getActivity(), NotaPesanan.class);
                String orderid = tv_orderid.getText().toString();
                Log.d("orderID : ", orderid);
                n.putExtra("orderid", orderid);
                startActivity(n);
            }
        });

        return view;
    }

    private void getKeranjangList(String orderID) {

        if (orderID !=null)
        apiServices.getKeranjangPesanan(orderID)
                .enqueue(new Callback<GetKeranjangPesananResponse>() {
                    @Override
                    public void onResponse(Call<GetKeranjangPesananResponse> call, Response<GetKeranjangPesananResponse> response) {
                        List<GetKeranjangPesanan> list =response.body().getDataBody();
                        if (list != null){
                            cartAdapter.setProductsList(list);
                        }else{
                            Toast.makeText(getActivity(), "Keranjang kosong",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKeranjangPesananResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Gagal memuat Keranjang",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void detailpesanan(String orderID) {
        if (orderID !=null)
        apiServices.getPesananDetail(orderID)
                .enqueue(new Callback<GetPesananDetailResponse>() {
                    @Override
                    public void onResponse(Call<GetPesananDetailResponse> call, Response<GetPesananDetailResponse> response) {
                        if(response.body() != null){
                            DataBodyPesanan dataBodyPesanan = response.body().getDataBody();
                            GetPesananDetail getPesananDetail = dataBodyPesanan.get0();

                            tv_tglpesan.setText(getPesananDetail.getCreatedAt());
                            tvjenisbayar.setText(getPesananDetail.getMetodePembayaran());
                            tvjeniskirim.setText(getPesananDetail.getMetodePengiriman());
                            tvtglantar.setText(getPesananDetail.getTanggalPengantaran());
                            tvtglterima.setText(getPesananDetail.getTanggalDiterima());
                            tvalamat.setText(getPesananDetail.getAlamat());
                            tvkota.setText(getPesananDetail.getKota());
                            tvtelp.setText(getPesananDetail.getNoTelp());
                        }
                        else {
                            Toast.makeText(getActivity(), "pesanan kosong, silahkan pesan terlebih dahulu",
                                    Toast.LENGTH_LONG).show();
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
