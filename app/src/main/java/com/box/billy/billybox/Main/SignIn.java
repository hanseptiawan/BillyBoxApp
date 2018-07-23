package com.box.billy.billybox.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.AuthSignIn;
import com.box.billy.billybox.Model.DataBodyUser;
import com.box.billy.billybox.Model.GetCartIDResponse;
import com.box.billy.billybox.Model.GetUser2;
import com.box.billy.billybox.Model.GetUserResponse2;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    SessionManager sessionManager;
        ApiServices apiServices;
//    ApiServicesLokal apiServices;
    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        Button btn_signin = findViewById(R.id.btn_masuk);
        TextView tv_signup = findViewById(R.id.tv_signup);
        ImageView iv_close = findViewById(R.id.img_close_signin);

        sessionManager = new SessionManager(getApplicationContext());

        apiServices = ApiUtils.getApiServices();

        et_username.setFocusable(true);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if(Validation(username, password)){
                    getusersession(username);
                    authentication(username, password);
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean Validation(String username, String password){
        if (username == null || username.trim().length() == 0){
            Toast.makeText(this, "Isi Username terlebih dahulu",
                    Toast.LENGTH_LONG).show();
            et_username.setFocusable(true);
            return false;
        }
        if (password == null || password.trim().length() == 0){
            Toast.makeText(this, "Isi Password terlebih dahulu",
                    Toast.LENGTH_LONG).show();
            et_password.setFocusable(true);
            return false;
        }
        return true;
    }

    public void getusersession(String username){
        Log.d("username parameter :", username);
        apiServices.getUser2(username)
                .enqueue(new Callback<GetUserResponse2>() {
                    @Override
                    public void onResponse(Call<GetUserResponse2> call, Response<GetUserResponse2> response) {
                        if(response.body() != null){
                            DataBodyUser dataBodyUser = response.body().getDataBody();
                            GetUser2 getUser2 = dataBodyUser.get0();
                            if (getUser2 != null){
                                String userid = getUser2.getUserId();
                                String img = getUser2.getMediaUrl();
                                String fnama = getUser2.getNamaDepan();
                                String lname = getUser2.getNamaBelakang();
                                String ttl = getUser2.getTglLahir();
                                String nohp = getUser2.getNoTelp();
                                String alamat = getUser2.getAlamat();
                                String username = getUser2.getUsername();
                                String password = getUser2.getPassword();
                                if (img != null){
                                    Log.d("img ,", img);

                                }
                                Log.d("userID : ", userid);
                                Log.d("fnama : ", fnama);
                                Log.d("lname : ", lname);
                                Log.d("nohp : ", nohp);
                                Log.d("alamat : ", alamat);
                                Log.d("username : ", username);
                                Log.d("password : ", password);

                                sessionManager.createLoginSession(userid, fnama, lname,
                                        username,password,alamat,nohp,ttl,img);
                            }else {
                                Log.d("img ,", String.valueOf(getUser2));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUserResponse2> call, Throwable t) {
                        Toast.makeText(SignIn.this, "Get User data Failed",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void authentication(final String username, String password){
        apiServices.login(username, password)
                .enqueue(new Callback<AuthSignIn>() {
                    @Override
                    public void onResponse(Call<AuthSignIn> call, Response<AuthSignIn> response) {
                        String a = et_username.getText().toString().trim();
                        Log.d("et_username :  ", a);
                        if(response.isSuccessful()){
                            Toast.makeText(SignIn.this, "Authentication Success",
                                    Toast.LENGTH_LONG).show();
                            Log.d("Respon",": "+response);
                            Intent n = new Intent(SignIn.this, MainMember.class);
                            startActivity(n);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthSignIn> call, Throwable t) {
                        Toast.makeText(SignIn.this, "Username belum terdaftar" + t,
                                Toast.LENGTH_LONG).show();
                        et_username.setText("");
                        et_password.setText("");
                        et_username.setFocusable(true);
                        Log.d("Hara",":"+t);
                    }
                });
    }
}
