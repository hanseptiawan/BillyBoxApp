package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.box.billy.billybox.Main.PesananDetail;
import com.box.billy.billybox.Model.GetPesanan;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        if (Objects.equals(getPesanan.get(position).getTanggalDiterima(), "0000-00-00"))
            holder.tv_tglterima.setText("Belum diterima");

        holder.tv_status.setText(getPesanan.get(position).getStatus());

        if(getPesanan.get(position).getShipment() == null){
            holder.tv5.setVisibility(View.GONE);
            holder.tvshpment.setVisibility(View.GONE);
        }
        else{
            holder.tv5.setVisibility(View.VISIBLE);
            holder.tvshpment.setVisibility(View.VISIBLE);
            holder.tvshpment.setText(getPesanan.get(position).getShipment());
        }



    }

    @Override
    public int getItemCount() {
        return getPesanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_idpesanan, tv_tglantar, tv_tglterima, tv_status, tvshpment, tv5;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            tv5 = itemView.findViewById(R.id.tv5);
            tv_idpesanan = itemView.findViewById(R.id.tv_idpesanan);
            tv_tglantar = itemView.findViewById(R.id.tv_tglantar);
            tv_tglterima = itemView.findViewById(R.id.tv_tglterima);
            tv_status = itemView.findViewById(R.id.tv_status);
            cardView = itemView.findViewById(R.id.card_areapesanan);
            tvshpment = itemView.findViewById(R.id.tv_shipmet);

            cardView.setClickable(true);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            String idpesanan = getPesanan.get(position).getOrderId();
            String noBank = getPesanan.get(position).getNoBank();
            String nama = getPesanan.get(position).getNama();
            String nominal = getPesanan.get(position).getNominal();
            String idpayment = getPesanan.get(position).getPaymentId();
            String status = getPesanan.get(position).getStatus();

            Log.d("Order ID : ", idpesanan);

            PesananDetail fragment = new PesananDetail();
            FragmentTransaction fragmentTransaction = ((FragmentActivity)context)
                    .getSupportFragmentManager()
                    .beginTransaction();

            Bundle bundle = new Bundle();

            bundle.putString("idpesanan", idpesanan);
            bundle.putString("idpayment", idpayment);
            bundle.putString("noBank", noBank);
            bundle.putString("nama", nama);
            bundle.putString("nominal", nominal);
            bundle.putString("status", status);

            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
