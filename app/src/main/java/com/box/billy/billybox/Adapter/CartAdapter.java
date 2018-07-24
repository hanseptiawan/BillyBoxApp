package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.box.billy.billybox.Main.Main;
import com.box.billy.billybox.Main.MainMember;
import com.box.billy.billybox.Model.DeleteCartItemResponse;
import com.box.billy.billybox.Model.GetCart;
import com.box.billy.billybox.R;
import com.box.billy.billybox.Rest.ApiServices;
import com.box.billy.billybox.Rest.ApiServicesLokal;
import com.box.billy.billybox.Rest.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by han on 7/18/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<GetCart> getProducts;
    private LayoutInflater layoutInflater;
    private Context context;
    ApiServices apiServices;
    private int sum = 0;
    private static boolean add = true;
//    ApiServicesLokal apiServices;

    public CartAdapter(Context context) {
        this.getProducts = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setProductsList(List<GetCart> getProductList){
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
        holder.tv_namaproduct.setText(getProducts.get(position).getNamaItem());
        holder.tv_productid.setText(getProducts.get(position).getItemId());
        int harga = Integer.parseInt(getProducts.get(position).getHarga());
        int jumlah = Integer.parseInt(getProducts.get(position).getJumlah());
        int total = harga * jumlah;
        holder.tv_hargatotal.setText("Rp. " + Integer.toString(total) + ",-");
        holder.et_jumlah.setText(getProducts.get(position).getJumlah());
    }

    @Override
    public int getItemCount() {
        return getProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_delete;
        TextView tv_namaproduct, tv_productid, tv_hargatotal;
        EditText et_jumlah;

        public ViewHolder(View itemView) {
            super(itemView);

            apiServices = ApiUtils.getApiServices();
            iv_delete = itemView.findViewById(R.id.iv_delete);
            tv_namaproduct = itemView.findViewById(R.id.tv_namaproduct);
            tv_productid = itemView.findViewById(R.id.tv_productid);
            tv_hargatotal = itemView.findViewById(R.id.tv_hargatotal);
            et_jumlah = itemView.findViewById(R.id.et_jumlah);
            et_jumlah.setFocusable(false);
            et_jumlah.setEnabled(false);

            iv_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            String detailcartID = getProducts.get(position).getDetailCartId();

            if (detailcartID != null){
                deleteitem(detailcartID);
            }else {
                Toast.makeText(context, "Item tidak dikenali",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void deleteitem(String detailcartID) {
        apiServices.deleteItem(detailcartID)
                .enqueue(new Callback<DeleteCartItemResponse>() {
                    @Override
                    public void onResponse(Call<DeleteCartItemResponse> call, Response<DeleteCartItemResponse> response) {
                        Intent a = new Intent(context, MainMember.class);
                        context.startActivity(a);
                        Toast.makeText(context, "Item berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DeleteCartItemResponse> call, Throwable t) {
                        Toast.makeText(context, "Item gagal dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
