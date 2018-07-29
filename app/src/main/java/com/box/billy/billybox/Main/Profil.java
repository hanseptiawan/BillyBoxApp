package com.box.billy.billybox.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Model.DataBodyUser;
import com.box.billy.billybox.Model.GetUser2;
import com.box.billy.billybox.Model.GetUserResponse2;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Profil extends Fragment {

    SessionManager sessionManager;
    TextView tv_name1, ttl, tv_nohp, tv_address, username, password;
    CircleImageView circleImageView;
        ImageView iv_edit;
    ApiServices apiServices;
//    ApiServicesLokal apiServices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        apiServices = ApiUtils.getApiServices();
        sessionManager = new SessionManager(getContext());

        circleImageView = view.findViewById(R.id.circular_profile);
        tv_name1 = view.findViewById(R.id.tv_nama);
        ttl = view.findViewById(R.id.tv_tt2);
        tv_nohp = view.findViewById(R.id.tv_nohp2);
        tv_address = view.findViewById(R.id.tv_alamat2);
        username = view.findViewById(R.id.tv_username2);
        password = view.findViewById(R.id.tv_password2);
        iv_edit = view.findViewById(R.id.iv_edit);

        final HashMap<String, String> user = sessionManager.getUserDetails();
        final String userID = user.get(sessionManager.KEY_ID);

        Log.d("username : ", userID);
        getUser(userID);

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent (getContext(), EditProfil.class);
                n.putExtra("userID", userID);
                startActivity(n);
            }
        });

        return view;
    }

    private void getUser(String userID) {
        Log.d("perimeter user id : ", userID);
        apiServices.getUserbyID(userID)
                .enqueue(new Callback<GetUserResponse2>() {
                    @Override
                    public void onResponse(Call<GetUserResponse2> call, Response<GetUserResponse2> response) {
                        if (response.body() != null){
                            DataBodyUser dataBodyUser = response.body().getDataBody();
                            GetUser2 getUser2 = dataBodyUser.get0();
                            String fname = getUser2.getNamaDepan();
                            String lname = getUser2.getNamaBelakang();
                            String img = getUser2.getMediaUrl();
                            String name = fname + " " + lname;

                            tv_name1.setText(name);
                            tv_name1.setAllCaps(true);
                            ttl.setText(getUser2.getTglLahir());
                            tv_nohp.setText(getUser2.getNoTelp());
                            tv_address.setText(getUser2.getAlamat());
                            username.setText(getUser2.getUsername());
                            password.setText(getUser2.getPassword());

                            if (img != null){
                                Glide.with(getActivity().getApplicationContext())
                                        .load(img)
                                        .fitCenter()
                                        .placeholder(R.drawable.ic_noimg)
                                        .error(R.drawable.ic_broken_image)
                                        .into(circleImageView);
                            }else {
                                //nothing
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUserResponse2> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal memuat informasi user",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void decode(String imgencoded) {
        if (imgencoded != null){
            Log.d("img base 64 : ", imgencoded);
            byte[] data = Base64.decode(imgencoded, Base64.DEFAULT);
            Bitmap decodedbyte = BitmapFactory.decodeByteArray(data,0,data.length);

            circleImageView.setImageBitmap(decodedbyte);
        }else {
            Log.d("base64 null : ", "tidak ada string untuk decode");
        }
    }
}
