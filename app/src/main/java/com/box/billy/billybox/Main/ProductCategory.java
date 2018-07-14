package com.box.billy.billybox.Main;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.box.billy.billybox.Adapter.ProductCatAdapter;
import com.box.billy.billybox.Model.GetProductCat;
import com.box.billy.billybox.Model.GetProductCatResponse;
import com.box.billy.billybox.Model.SessionManager;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategory extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProductCatAdapter productCatAdapter;
    ApiServices apiServices;
    ProgressDialog progressDialog;


    public ProductCategory(){
        // empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productcat, container, false);

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

        return view;
    }

    private void kategorikarton() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat data ...");
        progressDialog.show();
        apiServices.getProductCatResponse("success")
                .enqueue(new Callback<GetProductCatResponse>() {
                    @Override
                    public void onResponse(Call<GetProductCatResponse> call, Response<GetProductCatResponse> response) {
                        progressDialog.hide();
                        List<GetProductCat> list = response.body().getDataBody();
                        productCatAdapter.setProductCatsList(list);
                    }

                    @Override
                    public void onFailure(Call<GetProductCatResponse> call, Throwable t) {
                        progressDialog.hide();
                        Toast.makeText(getActivity(), "Gagal memuat kategori product",
                                Toast.LENGTH_LONG).show();
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
