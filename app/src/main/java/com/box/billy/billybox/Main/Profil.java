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

import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Profil extends Fragment {

    SessionManager sessionManager;
    TextView tv_name1, ttl, tv_nohp, tv_address, username, password;
    CircleImageView circleImageView;
        ApiServices apiServices;
        ImageView iv_edit;
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

        HashMap<String, String> userinfo = sessionManager.getUserDetails();
        String imguser = userinfo.get(sessionManager.KEY_IMG);

        if (imguser != null){
            Glide.with(getActivity().getApplicationContext())
                    .load(imguser)
                    .fitCenter()
                    .placeholder(R.drawable.ic_noimg)
                    .error(R.drawable.ic_broken_image)
                    .into(circleImageView);
        }
        else if (imguser == null){
            HashMap<String, String> imgsession = sessionManager.getImg();
            String a = imgsession.get(sessionManager.KEY_IMGBASE64);

            decode(a);
        }

        HashMap<String, String> user = sessionManager.getUserDetails();
        final String userID = user.get(sessionManager.KEY_ID);
        final String fname = user.get(sessionManager.KEY_FNAME);
        final String lname = user.get(sessionManager.KEY_LNAME);
        String username_key = user.get(sessionManager.KEY_USERNAME);
        String password_key = user.get(sessionManager.KEY_PASSWORD);
        String address = user.get(sessionManager.KEY_ADDR);
        String phone = user.get(sessionManager.KEY_PHONE);
        String ttl_key = user.get(sessionManager.KEY_TTL);

        //implemen ke layout
        String name = fname + " " + lname;

        tv_name1.setText(name);
        tv_name1.setAllCaps(true);
        ttl.setText(ttl_key);
        tv_nohp.setText(phone);
        tv_address.setText(address);
        username.setText(username_key);
        password.setText(password_key);

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent (getContext(), EditProfil.class);
                n.putExtra("fname", userID);
                n.putExtra("fname", fname);
                n.putExtra("lname", lname);
                n.putExtra("ttl", ttl.getText().toString());
                n.putExtra("nohp", tv_nohp.getText().toString());
                n.putExtra("address", tv_address.getText().toString());
                n.putExtra("username", username.getText().toString());
                n.putExtra("password", password.getText().toString());
                startActivity(n);
            }
        });

        return view;
    }

    private void decode(String imgencoded) {
        byte[] data = Base64.decode(imgencoded, Base64.DEFAULT);
        Bitmap decodedbyte = BitmapFactory.decodeByteArray(data,0,data.length);

        circleImageView.setImageBitmap(decodedbyte);
    }
}
