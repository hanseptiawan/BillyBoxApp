package com.box.billy.billybox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
        RelativeLayout rl_area;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.card_title);
            rl_area = itemView.findViewById(R.id.card_areacat);

            rl_area.setClickable(true);
            rl_area.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Intent intent = new Intent(context, Product.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("catID", getProductCats.get(position).getCategoryCartonId());
            intent.putExtra("catName", getProductCats.get(position).getNama());

            context.startActivity(intent);
        }
    }
}
