package com.box.billy.billybox.Main.Order;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

    TextView cartid, totalbayar;
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
        String total = getIntent().getStringExtra("totalpayment");

        final int totalint = Integer.valueOf(total);

        totalbayar.setText("Rp. " + total + ",-");

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
                    alamat.setEnabled(false);
                    kota.setEnabled(false);
                    notelp.setEnabled(false);
                }else {
                    bayarcod.setEnabled(true);
                    tfbank.setEnabled(false);
                    bayarcod.setChecked(true);
                    alamat.setEnabled(false);
                    kota.setEnabled(false);
                    notelp.setEnabled(false);
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
                    alamat.setEnabled(false);
                    kota.setEnabled(false);
                    notelp.setEnabled(false);
                }else {
                    bayarcod.setEnabled(false);
                    tfbank.setEnabled(true);
                    tfbank.setChecked(true);
                    alamat.setEnabled(false);
                    kota.setEnabled(false);
                    notelp.setEnabled(false);
                }
            }
        });

        dp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    double totaldp = totalint * 0.5;
                    totalbayar.setText("Rp. " + String.valueOf(totaldp) + ",-");
                }else {
                    totalbayar.setText("Rp. " + totalint + ",-");
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

                authentication(userid, pembayaran, pengiriman,
                        tglantar, malamat, mkota, mtelp, CID);
            }
        });
    }

    private void authentication(String userid, String pembayaran,
                                String pengiriman, String tglantar,
                                String malamat, String mkota,
                                String mtelp, String cid) {
    apiServices.postOrder(userid, pembayaran, pengiriman,
            tglantar, malamat, mkota, mtelp, cid)
            .enqueue(new Callback<PostOrder>() {
                @Override
                public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {
                    Log.d("response : ", String.valueOf(response));
                    Toast.makeText(OrderCheckout.this, "Pesanan telah dikirim, ",
                            Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(OrderCheckout.this, OrderCheckout.class);
                    startActivity(a);
                    finish();

                }

                @Override
                public void onFailure(Call<PostOrder> call, Throwable t) {
                    Log.d("response failure : ", String.valueOf(t));
                    Toast.makeText(OrderCheckout.this, "Gagal mengirim pesanan, " +t,
                            Toast.LENGTH_SHORT).show();
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
