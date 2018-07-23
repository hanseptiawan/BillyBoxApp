package com.box.billy.billybox.Main.Order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.box.billy.billybox.R;

/**
 * Created by han on 7/12/2018.
 */

public class OrderCheckout extends AppCompatActivity {

    TextView cartid, totalbayar;
    RadioButton delivery, cod, tfbank, bayarcod;
    CheckBox dp;
    EditText alamat, kota, notelp;
    Button btn_lanjut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cartid = findViewById(R.id.tv_cartid);
        totalbayar = findViewById(R.id.tv_totalbayar);
//        delivery = findViewById(R.id)

        String CID = getIntent().getStringExtra("cartid");

        if (CID != null){

        }
    }
}
