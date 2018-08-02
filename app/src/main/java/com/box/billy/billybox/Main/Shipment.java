package com.box.billy.billybox.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.ShipmentAdapter;
import com.box.billy.billybox.Model.GetShipment;
import com.box.billy.billybox.Model.GetShipmentResponse;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.DivideRecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 8/1/2018.
 */

public class Shipment extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ApiServices apiServices;
    ShipmentAdapter shipmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        apiServices = ApiUtils.getApiServices();

        String idpayment = getIntent().getStringExtra("idpayment");
        Log.d("on Shipment : ", idpayment);
        recyclerView = findViewById(R.id.recycle_view_shipment);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DivideRecyclerView(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        shipmentAdapter = new ShipmentAdapter(getApplicationContext());
        recyclerView.setAdapter(shipmentAdapter);

        if (idpayment != null){
            getShipmentList(idpayment);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getShipmentList(String idpayment) {
        apiServices.getShipment("PAY-20180717050721613202833")
                .enqueue(new Callback<GetShipmentResponse>() {
                    @Override
                    public void onResponse(Call<GetShipmentResponse> call, Response<GetShipmentResponse> response) {
                        List<GetShipment> list = response.body().getDataBody();
                        Log.d("onResponse: ", String.valueOf(response.body().getCode()));
                        if (list != null)
                            shipmentAdapter.setShipmentList(list);
                        else
                            Toast.makeText(Shipment.this, "Belum ada informasi pengiriman terbaru", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<GetShipmentResponse> call, Throwable t) {
                        Toast.makeText(Shipment.this, "Koneksi gagal, silahkan cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
