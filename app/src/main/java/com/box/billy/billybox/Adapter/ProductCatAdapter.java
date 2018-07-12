package com.box.billy.billybox.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.box.billy.billybox.Main.Product;
import com.box.billy.billybox.Main.ProductCategory;
import com.box.billy.billybox.Model.GetProductCat;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCatAdapter extends RecyclerView.Adapter<ProductCatAdapter.ViewHolder> {

    private List<GetProductCat> getProductCats;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductCatAdapter(Context context){
        this.getProductCats = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setProductCatsList(List<GetProductCat> getProductCatsList){
        this.getProductCats.clear();
        this.getProductCats.addAll(getProductCatsList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater
                .inflate(R.layout.list_productcat, parent,
                false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(getProductCats.get(position).getNama());

    }

    @Override
    public int getItemCount() {
        return getProductCats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        CardView rl_area;
        FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.card_title);
            rl_area = itemView.findViewById(R.id.card_areacat);
            frameLayout = itemView.findViewById(R.id.fragment_container);

            rl_area.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            String nama = getProductCats.get(position).getNama();
            String id = getProductCats.get(position).getCategoryCartonId();

            Product fragment = new Product();
            FragmentTransaction fragmentManager =((FragmentActivity)context)
                    .getSupportFragmentManager()
                    .beginTransaction();
            Bundle bundle=new Bundle();
            bundle.putString("catid", id); //key and value
            bundle.putString("catname", nama); //key and value
            fragment.setArguments(bundle);
            fragmentManager.replace(R.id.fragment_container, fragment);
            fragmentManager.addToBackStack(null);
            fragmentManager.commit();
        }
    }
}
