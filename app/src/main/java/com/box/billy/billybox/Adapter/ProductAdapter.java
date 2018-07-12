package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.box.billy.billybox.Main.Product;
import com.box.billy.billybox.Main.ProductCategory;
import com.box.billy.billybox.Model.GetProduct;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private List<GetProduct> getProducts;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductAdapter(Context context) {
        this.getProducts = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setProductList (List<GetProduct> getProductList){
        this.getProducts.clear();
        this.getProducts.addAll(getProductList);
        notifyDataSetChanged();
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater
                .inflate(R.layout.list_product, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        holder.namaproduct.setText(getProducts.get(position).getNama());

    }

    @Override
    public int getItemCount() {
        return getProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaproduct, stockproduct, hargajual, ukuran, img;

        public ViewHolder(View itemView) {
            super(itemView);

            namaproduct = itemView.findViewById(R.id.product_title);
            stockproduct = itemView.findViewById(R.id.product_stock);
            hargajual = itemView.findViewById(R.id.harga_product);
            ukuran = itemView.findViewById(R.id.ukuran_product);

//            img.setClickable(true);
//            img.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            int position = getAdapterPosition();
//
//            Intent intent = new Intent(context, ProductCategory.class);
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            intent.putExtra("cartonID", getProducts.get(position).getCartonId());
//            intent.putExtra("namaproduct", getProducts.get(position).getNamaItem());
//            intent.putExtra("kategoriproduct", getProducts.get(position).getCategoryCartonId());
//            intent.putExtra("stokproduct", getProducts.get(position).getStok());
//            intent.putExtra("hargajual", getProducts.get(position).getHargaJual());
//            intent.putExtra("ukuran", getProducts.get(position).getUkuran());
//            intent.putExtra("grameteur", getProducts.get(position).getGrametur());
//            intent.putExtra("imgproduct", getProducts.get(position).getImgSrc());
//            intent.putExtra("namakategoriproduct", getProducts.get(position).getNama());
//
//            context.startActivity(intent);
//
//        }
    }
}
