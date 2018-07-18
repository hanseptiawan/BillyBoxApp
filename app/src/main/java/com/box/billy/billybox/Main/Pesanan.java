package com.box.billy.billybox.Main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.PesananAdapter;
import com.box.billy.billybox.Model.GetPesanan;
import com.box.billy.billybox.Model.GetPesananResponse;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.DivideRecyclerView;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pesanan extends Fragment {

    SessionManager sessionManager;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PesananAdapter pesananAdapter;
    ApiServices apiServices;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesanan, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        HashMap<String, String> user = sessionManager.getUserDetails();
        String userID = user.get(sessionManager.KEY_ID);

        apiServices = ApiUtils.getApiServices();

        recyclerView = view.findViewById(R.id.recycle_view_pesanan);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DivideRecyclerView(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        pesananAdapter = new PesananAdapter(getContext());
        recyclerView.setAdapter(pesananAdapter);

        pesananbyuserID(userID);

        return view;
    }

    private void pesananbyuserID(String userID) {
        String foo = "PRF-2018051408054912";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat data ...");
        progressDialog.show();
        apiServices.getPesanan(foo)
                .enqueue(new Callback<GetPesananResponse>() {
                    @Override
                    public void onResponse(Call<GetPesananResponse> call, Response<GetPesananResponse> response) {
                        progressDialog.hide();
                        List<GetPesanan> list = response.body().getDataBody();
                        Log.e("list :  ", ""+list);
                        pesananAdapter.setPesananList(list);
                    }

                    @Override
                    public void onFailure(Call<GetPesananResponse> call, Throwable t) {
                        progressDialog.hide();
                        Log.d("hqq :" , String.valueOf(t));
                        Toast.makeText(getActivity(), "Gagal memuat pesanan",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
