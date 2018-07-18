package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.box.billy.billybox.Model.GetKeranjang;
import com.box.billy.billybox.Model.GetProduct;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 7/18/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<GetKeranjang> getProducts;
    private LayoutInflater layoutInflater;
    private Context context;
    SharedPreference sharedPreference;

    public CartAdapter(Context context) {
        this.getProducts = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        sharedPreference = new SharedPreference();
    }

    public void setProductsList(List<GetKeranjang> getProductList){
        this.getProducts.clear();
        this.getProducts.addAll(getProductList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater
                .inflate(R.layout.list_keranjang, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        holder.tv_namaproduct.setText(getProducts.get(position).getName());
        holder.tv_ukuranproduct.setText(getProducts.get(position).getUkuran());
        holder.tv_hargatotal.setText(getProducts.get(position).getHarga());

    }

    @Override
    public int getItemCount() {
        return getProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_delete;
        TextView tv_namaproduct, tv_ukuranproduct, tv_hargatotal;
        EditText et_jumlah;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_delete = itemView.findViewById(R.id.iv_delete);
            tv_namaproduct = itemView.findViewById(R.id.tv_namaproduct);
            tv_ukuranproduct = itemView.findViewById(R.id.tv_ukuranproduct);
            tv_hargatotal = itemView.findViewById(R.id.tv_hargatotal);
            et_jumlah = itemView.findViewById(R.id.et_jumlah);

            iv_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //delete product
        }
    }
}
