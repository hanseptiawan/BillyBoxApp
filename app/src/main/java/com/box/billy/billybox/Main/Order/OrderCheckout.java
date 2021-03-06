package com.box.billy.billybox.Main.Order;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Main.Keranjang;
import com.box.billy.billybox.Main.MainMember;
import com.box.billy.billybox.Main.Pesanan;
import com.box.billy.billybox.Main.Product;
import com.box.billy.billybox.Model.GetCartIDResponse;
import com.box.billy.billybox.Model.PostOrder;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.Datepicker_Fragment;
import com.box.billy.billybox.Utils.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 7/12/2018.
 */

public class OrderCheckout extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView cartid, totalbayar, tv7, tv8;
    RadioButton delivery, cod, tfbank, bayarcod;
    RadioGroup rg1, rg2;
    CheckBox dp;
    EditText alamat, kota, notelp, ettglantar;
    Button btn_lanjut;
    ImageView ivback, ivcalendar;

    SessionManager sessionManager;
    ApiServices apiServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        sessionManager = new SessionManager(getApplicationContext());
        apiServices = ApiUtils.getApiServices();

        cartid = findViewById(R.id.tv_cartid);

        tv7 = findViewById(R.id.textView7);
        tv8 = findViewById(R.id.textView8);
        delivery = findViewById(R.id.rb_delivery);
        cod = findViewById(R.id.rb_cod);
        tfbank = findViewById(R.id.rb_transfer);
        bayarcod = findViewById(R.id.rb_bayarcod);
        dp = findViewById(R.id.cb_dp);
        alamat = findViewById(R.id.et_alamat);
        kota = findViewById(R.id.et_kota);
        notelp = findViewById(R.id.et_notelp);
        btn_lanjut = findViewById(R.id.btn_kirimorder);
        totalbayar = findViewById(R.id.tv_totalbayar);
        ivback = findViewById(R.id.iv_backcheckout);
        rg1 = findViewById(R.id.rg_1);
        rg2 = findViewById(R.id.rg_2);
        ivcalendar = findViewById(R.id.iv_calendar);
        ettglantar = findViewById(R.id.et_tglantar);
        ettglantar.setEnabled(false);

        HashMap<String, String> userinfo = sessionManager.getUserDetails();
        final String userid = userinfo.get(sessionManager.KEY_ID);
        final String CID = getIntent().getStringExtra("cartid");
        final int total = getIntent().getIntExtra("totalpayment", 0);
        Log.d("onCreate: ", String.valueOf(total));
        cartid.setText(CID);

        totalbayar.setText("Rp. " + String.valueOf(total) + ",-");

        ivcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new Datepicker_Fragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    bayarcod.setEnabled(false);
                    tfbank.setEnabled(true);
                    tfbank.setChecked(true);

                    dp.setVisibility(View.VISIBLE);
                    alamat.setVisibility(View.VISIBLE);
                    kota.setVisibility(View.VISIBLE);
                    notelp.setVisibility(View.VISIBLE);
                    tv7.setVisibility(View.VISIBLE);
                    tv8.setVisibility(View.VISIBLE);
                    ettglantar.setHint("Tanggal Pengantaran");
                }else {
                    bayarcod.setEnabled(true);
                    tfbank.setEnabled(false);
                    bayarcod.setChecked(true);

                    dp.setVisibility(View.GONE);
                    alamat.setVisibility(View.GONE);
                    kota.setVisibility(View.GONE);
                    notelp.setVisibility(View.GONE);
                    tv7.setVisibility(View.GONE);
                    tv8.setVisibility(View.GONE);
                    ettglantar.setHint("Tanggal Penjemputan");
                }
            }
        });

        cod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    bayarcod.setEnabled(true);
                    tfbank.setEnabled(false);
                    bayarcod.setChecked(true);

                    dp.setVisibility(View.GONE);
                    alamat.setVisibility(View.GONE);
                    kota.setVisibility(View.GONE);
                    notelp.setVisibility(View.GONE);
                    tv7.setVisibility(View.GONE);
                    tv8.setVisibility(View.GONE);
                    ettglantar.setHint("Tanggal Penjemputan");
                }else {
                    bayarcod.setEnabled(false);
                    tfbank.setEnabled(true);
                    tfbank.setChecked(true);

                    dp.setVisibility(View.VISIBLE);
                    alamat.setVisibility(View.VISIBLE);
                    kota.setVisibility(View.VISIBLE);
                    notelp.setVisibility(View.VISIBLE);
                    tv7.setVisibility(View.VISIBLE);
                    tv8.setVisibility(View.VISIBLE);
                    ettglantar.setHint("Tanggal Pengantaran");
                }
            }
        });

        dp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    double totaldp = total * 0.5;
                    totalbayar.setText("Rp. " + String.valueOf(totaldp) + ",-");
                }else {
                    totalbayar.setText("Rp. " + String.valueOf(total) + ",-");
                }
            }

        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pengiriman = delivery.isChecked() ? delivery.getText().toString() : cod.getText().toString();
                String pembayaran = tfbank.isChecked() ? tfbank.getText().toString() : bayarcod.getText().toString();
                String tglantar = ettglantar.getText().toString();
                String malamat = alamat.getText().toString();
                String mkota = kota.getText().toString();
                String mtelp = notelp.getText().toString();

                Log.d("pra-authentication : ", userid + " " +
                        pembayaran + " " +
                        pengiriman + " " +
                        tglantar + " " +
                        malamat + " " +
                        mkota + " " +
                        mtelp + " " +
                        CID);

                sessionManager.orderCommit(CID);
                getCartID(userid);
                authentication(userid, pembayaran, pengiriman,
                        tglantar, malamat, mkota, mtelp, CID);
            }
        });
    }

    private void authentication(final String userid, String pembayaran,
                                String pengiriman, String tglantar,
                                String malamat, String mkota,
                                String mtelp, final String cid) {

        Log.d("parameter kirim : ", userid+" "+
        pembayaran+" "+pengiriman+" "+tglantar+" "+malamat+" "+
        mkota+" "+mtelp+" "+cid);
    apiServices.postOrder(userid, pembayaran, pengiriman,
            tglantar, malamat, mkota, mtelp, cid)
            .enqueue(new Callback<PostOrder>() {
                @Override
                public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {
                    if (response.isSuccessful()){
                        Log.d("response : ", String.valueOf(response));
                        Toast.makeText(OrderCheckout.this, "Pesanan telah dikirim",
                                Toast.LENGTH_SHORT).show();
                        Log.d("CartID sblm dikirim :", cid);
                        sessionManager.orderCommit(cid);
                        getCartID(userid);

                        Intent a = new Intent(OrderCheckout.this, MainMember.class);
                        startActivity(a);
                        finish();
                    }else {
                        Toast.makeText(OrderCheckout.this, "Gagal mengirim order",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PostOrder> call, Throwable t) {
                    Log.d("response failure : ", String.valueOf(t));
                    Toast.makeText(OrderCheckout.this, "Pesanan telah dikirim",
                            Toast.LENGTH_SHORT).show();

                    Intent a = new Intent(OrderCheckout.this, MainMember.class);
                    startActivity(a);
                    finish();
                }
            });
    }

    private void getCartID(String userid) {
        apiServices.getCartID(userid)
                .enqueue(new Callback<GetCartIDResponse>() {
                    @Override
                    public void onResponse(Call<GetCartIDResponse> call, Response<GetCartIDResponse> response) {
                        if (response.isSuccessful()){
                            String getcartID = response.body().getDataBody();
                            Log.d("Cart ID : ", getcartID);

                            sessionManager.createCartID(getcartID);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCartIDResponse> call, Throwable t) {
                        Log.d("Cart ID : ", "gagal dibuat");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        ettglantar = findViewById(R.id.et_tglantar);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        ettglantar.setText(year+"-"+month+"-"+day);
    }
}
