package com.box.billy.billybox.Main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.ProductCatAdapter;
import com.box.billy.billybox.Model.GetProductCat;
import com.box.billy.billybox.Model.GetProductCatResponse;
import com.box.billy.billybox.Model.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategory extends Fragment {

    SessionManager sessionManager;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProductCatAdapter productCatAdapter;
    ApiServices apiServices;

//    public ProductCategory(){
//        // empty public constructor
//    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productcat, container, false);

        RelativeLayout relativeLayout = view.findViewById(R.id.card_area);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.checkLogin();

        apiServices = ApiUtils.getApiServices();

        recyclerView = view.findViewById(R.id.recycle_view_productcat);

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        productCatAdapter = new ProductCatAdapter(getContext());
        recyclerView.setAdapter(productCatAdapter);

        kategorikarton();



//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //nana
//            }
//        });

        return view;
    }

    private void kategorikarton() {
        apiServices.getProductCatResponse("success")
                .enqueue(new Callback<GetProductCatResponse>() {
                    @Override
                    public void onResponse(Call<GetProductCatResponse> call, Response<GetProductCatResponse> response) {
                        List<GetProductCat> list = response.body().getDataBody();
                        productCatAdapter.setProductCatsList(list);
                    }

                    @Override
                    public void onFailure(Call<GetProductCatResponse> call, Throwable t) {

                    }
                });
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;
        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
    }

    private int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.getDisplayMetrics()));
    }


}
