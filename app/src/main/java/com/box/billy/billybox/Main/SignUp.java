package com.box.billy.billybox.Main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.AuthSignUp;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.box.billy.billybox.Utils.Datepicker_Fragment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

        ApiServices apiServices;
//    ApiServicesLokal apiServices;
    EditText et_fname, et_lname, et_addr, et_ttl,
    et_nohp, et_username, et_password1, et_password2;
    ImageView iv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_addr = findViewById(R.id.et_address);
        et_ttl = findViewById(R.id.et_ttl);
        et_nohp = findViewById(R.id.et_nohp);
        et_username = findViewById(R.id.et_username);
        et_password1 = findViewById(R.id.et_password_signup1);
        et_password2 = findViewById(R.id.et_password_signup2);
        iv_date = findViewById(R.id.iv_date);

        et_ttl.setEnabled(false);
        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker = new Datepicker_Fragment();
                datepicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button btn_signup = findViewById(R.id.btn_submit);
        TextView tv_signin = findViewById(R.id.tv_signin);
        ImageView iv_back = findViewById(R.id.img_back);

        apiServices = ApiUtils.getApiServices();

        et_fname.setFocusable(true);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = et_fname.getText().toString().trim();
                String lname = et_lname.getText().toString().trim();
                String address = et_addr.getText().toString().trim();
                String ttl = et_ttl.getText().toString().trim();
                String nohp = et_nohp.getText().toString().trim();
                String username = et_username.getText().toString().trim();
                String password1 = et_password1.getText().toString().trim();
                String password2 = et_password2.getText().toString().trim();


                if(Validation(fname, lname, username, password1,
                        password2, address, nohp, ttl)){
                    authentication(fname,lname,username,password2,
                            address,nohp,ttl);
                }
            }
        });

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private boolean Validation(String fname,String lname,String username,String password1,
                               String password2,String address,String nohp,String ttl){
        if (fname == null || fname.trim().length() == 0){
            Toast.makeText(SignUp.this, "Silahkan isi nama depan anda",
                    Toast.LENGTH_LONG).show();
            et_fname.setFocusable(true);
            return false;
        }
        else if(lname == null || lname.trim().length() == 0){
            Toast.makeText(SignUp.this, "Silahkan isi nama belakang anda",
                    Toast.LENGTH_LONG).show();
            et_lname.setFocusable(true);
            return false;

        }
        else if(username == null || username.trim().length() == 0){
            Toast.makeText(SignUp.this, "Silahkan isi username anda",
                    Toast.LENGTH_LONG).show();
            et_username.setFocusable(true);
            return false;

        }
        else if(password1 == null || password1.trim().length() == 0){
            Toast.makeText(SignUp.this, "Silahkan isi password anda",
                    Toast.LENGTH_LONG).show();
            et_password1.setFocusable(true);
            return false;

        }
        else if(password2 == null || password2.trim().length() == 0){
            Toast.makeText(SignUp.this, "Silahkan konfirmasi password anda",
                    Toast.LENGTH_LONG).show();
            et_password2.setFocusable(true);
            return false;

        }
        else if(!password1.equals(password2)){
            Toast.makeText(SignUp.this, "Konfirmasi password tidak sama, silahkan diulangi",
                    Toast.LENGTH_LONG).show();
            et_password1.setText("");
            et_password2.setText("");
            et_password1.setFocusable(true);
            return false;

        }
        else if(address == null || address.trim().length() == 0){
            Toast.makeText(SignUp.this, "Silahkan isi alamat anda",
                    Toast.LENGTH_LONG).show();
            et_addr.setFocusable(true);
            return false;

        }
        else if(nohp == null || nohp.trim().length() == 0){
            Toast.makeText(SignUp.this, "Silahkan isi nomor hp anda",
                    Toast.LENGTH_LONG).show();
            et_nohp.setFocusable(true);
            return false;

        }
        else if(ttl == null || ttl.trim().length() == 0){
            Toast.makeText(SignUp.this, "silahkan isi tanggal lahir anda",
                    Toast.LENGTH_LONG).show();
            et_ttl.setFocusable(true);
            return false;
        }
        return true;
    }

    public void authentication(final String fname, final String lname, final String username, final String password2,
                               final String address, final String nohp, final String ttl){
        Log.e("Isi",address+nohp+ttl);
        apiServices.signUp(fname,lname,username,password2,address,nohp,ttl)
                .enqueue(new Callback<AuthSignUp>() {
                    @Override
                    public void onResponse(Call<AuthSignUp> call, Response<AuthSignUp> response) {
                        Log.e("Call",""+call);
                        if(response.isSuccessful()){
                            Toast.makeText(SignUp.this, "Pendaftaran Berhasil",
                                    Toast.LENGTH_LONG).show();
                            Log.d("respon", " : "+ response);
                            Intent n = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(n);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthSignUp> call, Throwable t) {
                        Toast.makeText(SignUp.this, "Connection Failure, please check your connection" + t,
                                Toast.LENGTH_LONG).show();

                        et_fname.setFocusable(true);
                        Log.d("Hara",":"+t);
                    }
                });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        EditText et_ttl = findViewById(R.id.et_ttl);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        et_ttl.setText(year+"-"+month+"-"+day);
    }
}
