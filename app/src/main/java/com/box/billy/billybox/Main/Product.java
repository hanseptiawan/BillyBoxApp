package com.box.billy.billybox.Main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.box.billy.billybox.Adapter.ProductAdapter;
import com.box.billy.billybox.Adapter.ProductCatAdapter;
import com.box.billy.billybox.Model.GetProduct;
import com.box.billy.billybox.Model.GetProductResponse;
import com.box.billy.billybox.Model.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends Fragment {

    SessionManager sessionManager;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProductAdapter productAdapter;
    ApiServices apiServices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.checkLogin();

        apiServices = ApiUtils.getApiServices();

        recyclerView = view.findViewById(R.id.recycle_view_product);

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(getContext());
        recyclerView.setAdapter(productAdapter);

        Intent intent = getActivity().getIntent();

        String catID = intent.getStringExtra("catID");
        String catName = intent.getStringExtra("catName");
        String status = "success";

        productbykategori(catID,status);

        return view;
    }

    private void productbykategori(String catID, String status) {
        apiServices.getProductResponse(catID, status)
                .enqueue(new Callback<GetProductResponse>() {
                    @Override
                    public void onResponse(Call<GetProductResponse> call, Response<GetProductResponse> response) {
                        List<GetProduct> list = response.body().getDataBody();
                        productAdapter.setProductList(list);
                    }

                    @Override
                    public void onFailure(Call<GetProductResponse> call, Throwable t) {

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
