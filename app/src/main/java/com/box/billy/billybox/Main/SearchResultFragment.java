package com.box.billy.billybox.Main;

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
import android.widget.Toast;

import com.box.billy.billybox.Adapter.SearchResultAdapter;
import com.box.billy.billybox.Model.GetSearch;
import com.box.billy.billybox.Model.GetSearchResponse;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 8/2/2018.
 */

public class SearchResultFragment extends Fragment{

    ArrayList<GetSearch> mArrayList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchResultAdapter searchResultAdapter;
    ApiServices apiServices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchresult, container, false);

        apiServices = ApiUtils.getApiServices();
        String newtext = getArguments().getString("query_string");

        recyclerSetup(view);

        searchQuery(newtext);
        return view;
    }

    private void searchQuery(final String newtext) {
        apiServices.getSearch()
                .enqueue(new Callback<GetSearchResponse>() {
                    @Override
                    public void onResponse(Call<GetSearchResponse> call, Response<GetSearchResponse> response) {

                        GetSearchResponse getSearchResponse = response.body();
                        mArrayList = new ArrayList<>(Arrays.asList(getSearchResponse.getDataBody()));
                        searchResultAdapter = new SearchResultAdapter(mArrayList);
                        recyclerView.setAdapter(searchResultAdapter);

                        if (searchResultAdapter != null && newtext != null)
                            searchResultAdapter.getFilter().filter(newtext);
                    }

                    @Override
                    public void onFailure(Call<GetSearchResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Koneksi gagal, periksa koneksi internet anda",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void recyclerSetup(View view) {
        recyclerView = view.findViewById(R.id.rv_result);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
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
