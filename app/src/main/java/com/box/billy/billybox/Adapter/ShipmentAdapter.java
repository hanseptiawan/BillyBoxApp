package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.box.billy.billybox.Model.GetShipment;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 8/1/2018.
 */

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ViewHolder>{

    private List<GetShipment> getShipments;
    private Context context;
    private LayoutInflater layoutInflater;

    public ShipmentAdapter(Context context) {
        this.getShipments = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setShipmentList(List<GetShipment> getShipmentList){
        this.getShipments.clear();
        this.getShipments.addAll(getShipmentList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater
                .inflate(R.layout.list_shipment, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvidpayment.setText(getShipments.get(position).getIdPayment());
        holder.tvlokasi.setText(getShipments.get(position).getLokasi());
        holder.tvupdateat.setText(getShipments.get(position).getWaktu());

    }

    @Override
    public int getItemCount() {
        return getShipments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvidpayment, tvlokasi, tvupdateat;

        public ViewHolder(View itemView) {
            super(itemView);

            tvidpayment = itemView.findViewById(R.id.tv_payment_id);
            tvlokasi = itemView.findViewById(R.id.tv_lokasi);
            tvupdateat = itemView.findViewById(R.id.tv_updateat);

        }
    }
}
