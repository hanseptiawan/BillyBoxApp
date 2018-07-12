package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.box.billy.billybox.Main.ProductDetails;
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
        holder.namaproduct.setText(getProducts.get(position).getNamaItem());
        holder.stockproduct.setText(getProducts.get(position).getStok());
        String a = "Rp. ";
        String b = ",-";
        holder.hargajual.setText(a+""+getProducts.get(position).getHargaJual()+b);
        holder.ukuran.setText(getProducts.get(position).getUkuran());
    }

    @Override
    public int getItemCount() {
        return getProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView namaproduct, stockproduct, hargajual, ukuran, img;
        CardView product;

        public ViewHolder(View itemView) {
            super(itemView);

            product = itemView.findViewById(R.id.product_area);
            namaproduct = itemView.findViewById(R.id.product_title);
            stockproduct = itemView.findViewById(R.id.product_stock);
            hargajual = itemView.findViewById(R.id.harga_product);
            ukuran = itemView.findViewById(R.id.ukuran_product);

            product.setClickable(true);
            product.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            String cartonId = getProducts.get(position).getCartonId();
            String namaItem = getProducts.get(position).getNamaItem();
            String catID = getProducts.get(position).getCartonId();
            String stock = getProducts.get(position).getStok();
            String harga = getProducts.get(position).getHargaJual();
            String ukuran = getProducts.get(position).getUkuran();
            String grametur = getProducts.get(position).getGrametur();
            String img = getProducts.get(position).getImgSrc();
            String namacat = getProducts.get(position).getNama();

            ProductDetails fragment = new ProductDetails();
            FragmentTransaction fragmentManager =((FragmentActivity)context)
                    .getSupportFragmentManager()
                    .beginTransaction();
            Bundle bundle=new Bundle();
            bundle.putString("cartonId", cartonId); //key and value
            bundle.putString("namaItem", namaItem); //key and value
            bundle.putString("catID", catID); //key and value
            bundle.putString("stock", stock); //key and value
            bundle.putString("harga", harga); //key and value
            bundle.putString("ukuran", ukuran); //key and value
            bundle.putString("grametur", grametur); //key and value
            bundle.putString("img", img); //key and value
            bundle.putString("namacat", namacat); //key and value
            fragment.setArguments(bundle);
            fragmentManager.replace(R.id.fragment_container, fragment);
            fragmentManager.addToBackStack(null);
            fragmentManager.commit();

        }
    }
}
