package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.box.billy.billybox.Model.GetPesananDetail;
import com.box.billy.billybox.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by han on 7/13/2018.
 */

public class PesananDetailAdapter extends RecyclerView.Adapter<PesananDetailAdapter.ViewHolder>{

    private List<GetPesananDetail> getPesananDetails;
    private LayoutInflater layoutInflater;
    private Context context;

    public PesananDetailAdapter(Context context) {
        this.getPesananDetails = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setPesananDetails (List<GetPesananDetail> getPesananDetailList){
        this.getPesananDetails.clear();
        this.getPesananDetails.addAll(getPesananDetailList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater
                .inflate(R.layout.fragment_detailpesanan, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return getPesananDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
