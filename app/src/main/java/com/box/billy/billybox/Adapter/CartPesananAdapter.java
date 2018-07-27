package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Main.MainMember;
import com.box.billy.billybox.Model.DeleteCartItemResponse;
import com.box.billy.billybox.Model.GetCart;
import com.box.billy.billybox.Model.GetKeranjangPesanan;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 7/18/2018.
 */

public class CartPesananAdapter extends RecyclerView.Adapter<CartPesananAdapter.ViewHolder> {

    private List<GetKeranjangPesanan> getProducts;
    private LayoutInflater layoutInflater;
    private Context context;
    ApiServices apiServices;
    private int sum = 0;
    private static boolean add = true;
//    ApiServicesLokal apiServices;

    public CartPesananAdapter(Context context) {
        this.getProducts = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setProductsList(List<GetKeranjangPesanan> getProductList){
        this.getProducts.clear();
        this.getProducts.addAll(getProductList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater
                .inflate(R.layout.list_keranjangpesanan, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CartPesananAdapter.ViewHolder holder, int position) {
        holder.tv_namaproduct.setText(getProducts.get(position).getNamaItem());
        holder.tv_productid.setText(getProducts.get(position).getCartonId());
        int harga = Integer.parseInt(getProducts.get(position).getHargaJual());
        int jumlah = Integer.parseInt(getProducts.get(position).getJumlah());
        int total = harga * jumlah;
        holder.tv_hargatotal.setText("Rp. " + Integer.toString(total) + ",-");
        holder.et_jumlah.setText(getProducts.get(position).getJumlah());
    }

    @Override
    public int getItemCount() {
        return getProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_namaproduct, tv_productid, tv_hargatotal;
        EditText et_jumlah;

        public ViewHolder(View itemView) {
            super(itemView);

            apiServices = ApiUtils.getApiServices();
            tv_namaproduct = itemView.findViewById(R.id.tv_namaproduct);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            tv_hargatotal = itemView.findViewById(R.id.tv_hargatotal);
            et_jumlah = itemView.findViewById(R.id.et_jumlah);
            et_jumlah.setFocusable(false);
            et_jumlah.setEnabled(false);
        }
    }
}
