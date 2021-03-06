package com.box.billy.billybox.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import com.box.billy.billybox.Model.TerimaBarangResponse;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 7/13/2018.
 */

public class PesananDetail extends Fragment {

        ApiServices apiServices;
//    ApiServicesLokal apiServices;
    TextView tv_orderid, tv_tglpesan, tvnama,
        tvjenisbayar, tvjeniskirim, tvtglantar,
        tvtglterima, tvalamat, tvkota, tvtelp, tvstatus, tvtotalbayar;
    Button btnlacak, btn_konfirmasi, btnupload;
    ImageView iv_back;
    CartPesananAdapter cartPesananAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailpesanan, container, false);

        apiServices = ApiUtils.getApiServices();
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        tvnama = view.findViewById(R.id.tv_namapemesan);
        tv_orderid = view.findViewById(R.id.tv_orderID);
        tv_tglpesan = view.findViewById(R.id.tv_tglpesan2);
        tvjenisbayar = view.findViewById(R.id.tv_jenisbayar);
        tvjeniskirim = view.findViewById(R.id.tv_jeniskirim);
        tvtglantar = view.findViewById(R.id.tv_tglantar2);
        tvtglterima = view.findViewById(R.id.tv_tglterima);
        tvalamat = view.findViewById(R.id.tv_alamat);
        tvkota = view.findViewById(R.id.tv_kota2);
        tvtelp = view.findViewById(R.id.tv_telp2);
        btnlacak = view.findViewById(R.id.btn_lacakpesanan);
        recyclerView = view.findViewById(R.id.recycle_view_keranjang_pesanan);
        iv_back = view.findViewById(R.id.iv_back);
        btn_konfirmasi = view.findViewById(R.id.btn_konfirmasi);
        tvstatus = view.findViewById(R.id.tv_status);
        btnupload = view.findViewById(R.id.btn_uploadbukti);
        tvtotalbayar = view.findViewById(R.id.tv_totalpembayaran);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        cartPesananAdapter = new CartPesananAdapter(getContext());
        recyclerView.setAdapter(cartPesananAdapter);

        HashMap<String, String> session_data = sessionManager.getUserDetails();
        String fname = session_data.get(sessionManager.KEY_FNAME);
        String lname = session_data.get(sessionManager.KEY_LNAME);

        tvnama.setText(fname+" "+lname);

        final String orderID = getArguments().getString("idpesanan");
        final String idpayment = getArguments().getString("idpayment");
        final String status = getArguments().getString("status");

        Log.d("detail pesanan : ", orderID + " " + idpayment +" "+ status);

        if (getArguments() != null){
            Log.d("orderID pesanan : ", orderID);
            tvstatus.setText(status);
            tv_orderid.setText(orderID);

            String mstatus = tvstatus.getText().toString();
            String mjeniskirim = tvjeniskirim.getText().toString();

            validation(mstatus, mjeniskirim);

            getKeranjangList(orderID);
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

        btn_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idpayment = getArguments().getString("idpayment");
                if (idpayment != null){
                    doTerimaBarang(idpayment);
                }else {
                    Log.d("idpayment : ", "idpayment kosong");
                }
            }
        });

        btnlacak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(), Shipment.class);
                a.putExtra("idpayment", idpayment);
                Log.d("idpayment : ", idpayment);
                startActivity(a);
            }
        });

        return view;
    }

    private boolean validation(String mstatus, String mjeniskirim) {
        if (Objects.equals(mstatus, "draft")){
            btn_konfirmasi.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_disabled));
//            btn_konfirmasi.setBackgroundColor(Color.parseColor("#f59351"));
            btn_konfirmasi.setEnabled(false);
            btnupload.setVisibility(View.VISIBLE);
            btnupload.setEnabled(true);
            btnlacak.setVisibility(View.GONE);
            return false;
        }else if (Objects.equals(mstatus, "upload pembayaran")){
            btnupload.setVisibility(View.GONE);
            btn_konfirmasi.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_disabled));
//            btn_konfirmasi.setBackgroundColor(Color.parseColor("#f59351"));
            btn_konfirmasi.setEnabled(false);
            btnlacak.setVisibility(View.GONE);
            return false;
        }else if (Objects.equals(mstatus, "diproses")){
//            btn_konfirmasi.setBackgroundColor(Color.parseColor("#b54508"));
            btn_konfirmasi.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded));
            btn_konfirmasi.setEnabled(true);
            btnupload.setVisibility(View.GONE);
            btnlacak.setVisibility(View.VISIBLE);
            return false;
        }else if (Objects.equals(mstatus, "selesai")){
//            btn_konfirmasi.setBackgroundColor(Color.parseColor("#f59351"));
            btn_konfirmasi.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_disabled));
            btn_konfirmasi.setEnabled(false);
            btnupload.setVisibility(View.GONE);
            btnlacak.setVisibility(View.VISIBLE);
            return false;
        }else if (Objects.equals(mstatus, "COD") && Objects.equals(mjeniskirim, "jemput")){
//            btn_konfirmasi.setBackgroundColor(Color.parseColor("#b54508"));
            btn_konfirmasi.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded));
            btn_konfirmasi.setEnabled(true);
            btnupload.setVisibility(View.GONE);
            btnlacak.setVisibility(View.GONE);
            return false;
        }else if (Objects.equals(mstatus, "ditolak")){
            btnupload.setVisibility(View.GONE);
            btnlacak.setVisibility(View.GONE);
//            btn_konfirmasi.setBackgroundColor(Color.parseColor("#f59351"));
            btn_konfirmasi.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.btn_rounded_disabled));
            btn_konfirmasi.setEnabled(false);
            return false;
        }else if (Objects.equals(mjeniskirim, "Kirim") || Objects.equals(mjeniskirim, "kirim")){
            btnlacak.setVisibility(View.VISIBLE);
            btnupload.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void doTerimaBarang(String idpayment) {
        apiServices.terimaBarang(idpayment)
                .enqueue(new Callback<TerimaBarangResponse>() {
                    @Override
                    public void onResponse(Call<TerimaBarangResponse> call, Response<TerimaBarangResponse> response) {
                        Toast.makeText(getActivity(), "Terimaksaih telah menggunakan Billy Box App",
                                Toast.LENGTH_LONG).show();
                        btn_konfirmasi.setBackgroundColor(Color.parseColor("#f59351"));
                        btn_konfirmasi.setEnabled(false);
                    }

                    @Override
                    public void onFailure(Call<TerimaBarangResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Terimaksaih telah menggunakan Billy Box App",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getKeranjangList(String orderid) {

        if (orderid !=null)
        apiServices.getKeranjangPesanan(orderid)
                .enqueue(new Callback<GetKeranjangPesananResponse>() {
                    @Override
                    public void onResponse(Call<GetKeranjangPesananResponse> call, Response<GetKeranjangPesananResponse> response) {
                        List<GetKeranjangPesanan> list =response.body().getDataBody();
                        if (list != null){
                            cartPesananAdapter.setProductsList(list);

                            int total = 0;
                            for (int i = 0; i < list.size(); i++){
                                total = total + Integer.parseInt(list.get(i).getTotalHarga());

                                Log.d("total payment : ", String.valueOf(total));
                                tvtotalbayar.setText("Rp." + String.valueOf(total) + ",-");

                                final int finalTotal = total;
                                final String orderID = getArguments().getString("idpesanan");
                                final String idpayment = getArguments().getString("idpayment");
                                final String status = getArguments().getString("status");

                                btnupload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent n = new Intent(getActivity(), BuktiTransfer.class);
                                        n.putExtra("idpayment", idpayment);
                                        n.putExtra("status", status);
                                        Log.d("orderID : ", orderID);
                                        n.putExtra("orderid", orderID);
                                        n.putExtra("totalbayar",String.valueOf(finalTotal));
                                        n.putExtra("nama",tvnama.getText());

                                        startActivity(n);
                                    }
                                });
                            }


                        }else{
                            Toast.makeText(getActivity(), "Keranjang kosong",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetKeranjangPesananResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Koneksi internet gagal, mohon cek koneksi anda",
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
                            Log.d("tgl antar : ", getPesananDetail.getCreatedAt() + "tgl terima :" +getPesananDetail.getTanggalDiterima());

                            if (!Objects.equals(getPesananDetail.getTanggalDiterima(), "0000-00-00")){
                                tvtglterima.setText(getPesananDetail.getTanggalDiterima());
                            }else  {
                                tvtglterima.setText("Barang belum diterima");
                            }
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
