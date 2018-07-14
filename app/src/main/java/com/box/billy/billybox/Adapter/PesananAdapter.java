package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.box.billy.billybox.Main.PesananDetail;
import com.box.billy.billybox.Model.GetPesanan;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder>{

    private List<GetPesanan> getPesanan;
    private LayoutInflater layoutInflater;
    private Context context;

    public PesananAdapter(Context context) {
        this.getPesanan = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setPesananList (List<GetPesanan> getPesananList){
        this.getPesanan.clear();
        this.getPesanan.addAll(getPesananList);
        notifyDataSetChanged();
    }

    @Override
    public PesananAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater
                .inflate(R.layout.list_pesanan, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(PesananAdapter.ViewHolder holder, int position) {
        holder.tv_idpesanan.setText(getPesanan.get(position).getOrderId());
        holder.tv_tglantar.setText(getPesanan.get(position).getTanggalPengantaran());
        holder.tv_tglterima.setText(getPesanan.get(position).getTanggalDiterima());
        holder.tv_status.setText(getPesanan.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return getPesanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_idpesanan, tv_tglantar, tv_tglterima, tv_status;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_idpesanan = itemView.findViewById(R.id.tv_idpesanan);
            tv_tglantar = itemView.findViewById(R.id.tv_tglantar);
            tv_tglterima = itemView.findViewById(R.id.tv_tglterima);
            tv_status = itemView.findViewById(R.id.tv_status);
            cardView = itemView.findViewById(R.id.card_areapesanan);

            cardView.setClickable(true);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            String idpesanan = getPesanan.get(position).getOrderId();
            String userid = getPesanan.get(position).getUserId();
            String mt_bayar = getPesanan.get(position).getMetodePembayaran();
            String mt_kirim = getPesanan.get(position).getMetodePengiriman();
            String tgl_antar = getPesanan.get(position).getTanggalPengantaran();
            String tgl_terima = getPesanan.get(position).getTanggalDiterima();
            String status = getPesanan.get(position).getStatus();

            PesananDetail fragment = new PesananDetail();
            FragmentTransaction fragmentTransaction = ((FragmentActivity)context)
                    .getSupportFragmentManager()
                    .beginTransaction();

            Bundle bundle = new Bundle();

            bundle.putString("idpesanan", idpesanan);
            bundle.putString("userid", userid);
            bundle.putString("mt_bayar", mt_bayar);
            bundle.putString("mt_kirim", mt_kirim);
            bundle.putString("tgl_antar", tgl_antar);
            bundle.putString("tgl_terima", tgl_terima);
            bundle.putString("status", status);

            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
