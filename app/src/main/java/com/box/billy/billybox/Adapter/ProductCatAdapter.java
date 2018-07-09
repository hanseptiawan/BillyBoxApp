package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.box.billy.billybox.Main.ProductCategory;
import com.box.billy.billybox.Model.GetProductCat;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCatAdapter extends RecyclerView.Adapter<ProductCatAdapter.ViewHolder> {

    private List<GetProductCat> getProductCats;
    private LayoutInflater layoutInflater;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
