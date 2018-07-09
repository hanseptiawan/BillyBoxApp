package com.box.billy.billybox.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.box.billy.billybox.Model.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.HashMap;

public class Profil extends Fragment {

    SessionManager sessionManager;
    TextView tv_name1, ttl, tv_nohp, tv_address, username, password;
    ApiServices apiServices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        apiServices = ApiUtils.getApiServices();
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        tv_name1 = view.findViewById(R.id.tv_nama);
        ttl = view.findViewById(R.id.tv_tt2);
        tv_nohp = view.findViewById(R.id.tv_nohp2);
        tv_address = view.findViewById(R.id.tv_alamat2);
        username = view.findViewById(R.id.tv_username2);
        password = view.findViewById(R.id.tv_password2);

        HashMap<String, String> user = sessionManager.getUserDetails();
        String fname = user.get(sessionManager.KEY_FNAME);
        String lname = user.get(sessionManager.KEY_LNAME);
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

        return view;
    }
}
