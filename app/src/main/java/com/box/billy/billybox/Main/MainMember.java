package com.box.billy.billybox.Main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.SearchResultAdapter;
import com.box.billy.billybox.Model.GetCartIDResponse;
import com.box.billy.billybox.Model.GetSearch;
import com.box.billy.billybox.Model.GetSearchResponse;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Utils.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMember extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager sessionManager;

    private static final String TAG = MainMember.class.getSimpleName();
    private static final int TIME_INTERVAL = 2000;
    private long mBackpressed;
    private DrawerLayout drawerLayout;
    SearchView searchView;
    NavigationView navigationView;
    ApiServices apiServices;
//    ApiServicesLokal apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_member);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        apiServices = ApiUtils.getApiServices();

        toolbarSetup(savedInstanceState);

        beginEvent();

        ButtonListener();
    }

    private void beginEvent() {
        TextView userdisplay = findViewById(R.id.tv_userdisplay2_member);

        HashMap<String, String> user = sessionManager.getUserDetails();
        String userID = user.get(sessionManager.KEY_ID);
        String fname = user.get(sessionManager.KEY_FNAME);
        String lname = user.get(sessionManager.KEY_LNAME);
        String name = fname + " " + lname;
        Log.d(TAG, "onCreate: "+ userID);

        if (!sessionManager.checkOrderCommit()){
            getCart(userID);
        }else {
            HashMap<String, String> cartID = sessionManager.getCartID();
            String cartid = cartID.get(sessionManager.KEY_CARTID);
            if (cartid == null){
                getCart(userID);
                Log.d("request new cart ID :", "because cart ID null");
            }
            Log.d(TAG, "get old CartID from session : "+ cartid);
        }

        if(userdisplay.getText() == ""){
            userdisplay.setText(name);
        }
        else {
            String a = "Guest";
            userdisplay.setText(a);
        }
    }

    private void toolbarSetup(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar_home_member);
        setSupportActionBar(toolbar);

        TextView textView = findViewById(R.id.toolbar_tittle_member);
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "carioca.ttf");
        textView.setTypeface(typeface);

        drawerLayout = findViewById(R.id.drawer_layout_home_member);
        navigationView = findViewById(R.id.navigation_view_home_member);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProductCategory()).commit();
            navigationView.setCheckedItem(R.id.menu_beranda);
        }
    }

    public void getCart(String userid) {
        apiServices.getCartID(userid)
                .enqueue(new Callback<GetCartIDResponse>() {
                    @Override
                    public void onResponse(Call<GetCartIDResponse> call, Response<GetCartIDResponse> response) {
                        if (response.isSuccessful()){
                            String getCartID = response.body().getDataBody();
                            Log.d("cartID : ", getCartID);

                            sessionManager.createCartID(getCartID);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCartIDResponse> call, Throwable t) {

                    }
                });
    }

    public void ButtonListener(){
        ImageView iv_cart = findViewById(R.id.cart_button_member);
        Button btn_keluar = findViewById(R.id.btn_signout);

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Keranjang()).commit();
            }
        });

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (mBackpressed + TIME_INTERVAL > System.currentTimeMillis()) {
            moveTaskToBack(true);
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tekan sekali lagi untuk keluar",
                    Toast.LENGTH_SHORT).show();
        }
        mBackpressed = System.currentTimeMillis();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_beranda:
                Intent a = new Intent(this, MainMember.class);
                startActivity(a);
                finish();
                break;

            case R.id.menu_keranjang:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Keranjang()).commit();
                break;

            case R.id.menu_pesanan:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Pesanan()).commit();
                break;

            case R.id.menu_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Profil()).commit();
                break;

            case R.id.menu_ttgkami:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Tentangkami()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(false);
        search(searchView);

        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    SearchResultFragment searchResultFragment = new SearchResultFragment(); //your search fragment
                    Bundle args = new Bundle();
                    args.putString("query_string", newText);
                    searchResultFragment.setArguments(args);

                    transaction.replace(R.id.fragment_container, searchResultFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ProductCategory()).commit();
                    navigationView.setCheckedItem(R.id.menu_beranda);

                }
                return false;
            }
        });
    }
}
