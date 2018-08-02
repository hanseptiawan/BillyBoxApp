package com.box.billy.billybox.Main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.SearchResultAdapter;
import com.box.billy.billybox.Model.GetSearch;
import com.box.billy.billybox.Model.GetSearchResponse;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TIME_INTERVAL = 2000;
    private long mBackpressed;
    private DrawerLayout drawerLayout;
    ApiServices apiServices;
    SearchView searchView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        apiServices = ApiUtils.getApiServices();
        toolbarSetup(savedInstanceState);

        ButtonListener();
    }

    private void toolbarSetup(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        TextView textView = findViewById(R.id.toolbar_tittle_main);
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "carioca.ttf");
        textView.setTypeface(typeface);
        TextView userdisplay = findViewById(R.id.tv_userdisplay2);
        userdisplay.setText("Guest");

        drawerLayout = findViewById(R.id.drawer_layout_main);
        navigationView = findViewById(R.id.navigation_view_main);
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

    public void ButtonListener(){
        Button btn_masuk = findViewById(R.id.btn_signin);

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Main.this, SignIn.class);
                startActivity(a);
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
                Intent a = new Intent(this, Main.class);
                startActivity(a);
                finish();
                break;

            case R.id.menu_ttgkami:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Tentangkami()).commit();
                break;

            case R.id.menu_petunjuk:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Petunjuk()).commit();
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

    private void search(SearchView searchView){

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
