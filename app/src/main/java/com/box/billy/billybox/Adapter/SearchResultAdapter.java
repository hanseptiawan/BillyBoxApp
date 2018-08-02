package com.box.billy.billybox.Adapter;

import android.app.Activity;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.box.billy.billybox.Main.ProductDetails;
import com.box.billy.billybox.Model.GetProduct;
import com.box.billy.billybox.Model.GetSearch;
import com.box.billy.billybox.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> implements Filterable{

    private ArrayList<GetSearch> mArrayList;
    private ArrayList<GetSearch> mFilteredList;
    private Context context;

    public SearchResultAdapter(ArrayList<GetSearch> arrayList) {
        this.mArrayList = arrayList;
        this.mFilteredList = arrayList;
    }

    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent,
                        false);
        ViewHolder holder = new ViewHolder(view);
        context = parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(SearchResultAdapter.ViewHolder holder, int position) {


        holder.namaproduct.setText(mFilteredList.get(position).getNamaItem());
        holder.stockproduct.setText(mFilteredList.get(position).getStok());
        String a = "Rp. ";
        String b = ",-";
        holder.hargajual.setText(a+""+mFilteredList.get(position).getHargaJual()+b);
        holder.ukuran.setText(mFilteredList.get(position).getUkuran());

        Glide.with(context)
                .load(mFilteredList.get(position).getMediaUrl())
                .fitCenter()
                .placeholder(R.drawable.ic_noimg)
                .error(R.drawable.ic_broken_image)
                .into(holder.imgproduct);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()){
                    mFilteredList = mArrayList;
                }else {
                    ArrayList<GetSearch> searchArrayList = new ArrayList<>();

                    for (GetSearch getSearch : mArrayList) {
                        if (getSearch.getNamaItem().toLowerCase().contains(charString) || getSearch.getNama().toLowerCase().contains(charString)){
                            searchArrayList.add(getSearch);
                        }
                    }

                    mFilteredList = searchArrayList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<GetSearch>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView namaproduct, stockproduct, hargajual, ukuran, img;
        ImageView imgproduct;
        FrameLayout fl1;
        CardView product;

        public ViewHolder(View itemView) {
            super(itemView);

            product = itemView.findViewById(R.id.product_area);
            namaproduct = itemView.findViewById(R.id.product_title);
            stockproduct = itemView.findViewById(R.id.product_stock);
            hargajual = itemView.findViewById(R.id.harga_product);
            ukuran = itemView.findViewById(R.id.ukuran_product);
            imgproduct = itemView.findViewById(R.id.iv_imageproduct);
            fl1 = itemView.findViewById(R.id.fl1);

            product.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context c = view.getContext();
            int position = getAdapterPosition();

            String cartonId = mFilteredList.get(position).getCartonId();
            String namaItem = mFilteredList.get(position).getNamaItem();
            String catID = mFilteredList.get(position).getCategoryCartonId();
            Log.d( "Cat ID : ", catID);
            String stock = mFilteredList.get(position).getStok();
            String harga = mFilteredList.get(position).getHargaJual();
            String ukuran = mFilteredList.get(position).getUkuran();
            String grametur = mFilteredList.get(position).getGrametur();
            String img = mFilteredList.get(position).getMediaUrl();
            String namacat = mFilteredList.get(position).getNama();

            ProductDetails fragment = new ProductDetails();
            FragmentTransaction fragmentManager =((FragmentActivity)c)
                    .getSupportFragmentManager()
                    .beginTransaction();
            Bundle bundle=new Bundle();
            bundle.putString("cartonId", cartonId); //key and value
            Log.d( "Carton ID : ", cartonId);
            bundle.putString("namaItem", namaItem); //key and value
            bundle.putString("catID", catID); //key and value
            Log.d( "Cat ID : ", catID);
            bundle.putString("stock", stock); //key and value
            bundle.putString("harga", harga); //key and value
            bundle.putString("ukuran", ukuran); //key and value
            bundle.putString("grametur", grametur); //key and value
            bundle.putString("img", img); //key and value
            Log.d( "img url : ", img);
            bundle.putString("namacat", namacat); //key and value
            fragment.setArguments(bundle);
            fragmentManager.replace(R.id.fragment_container, fragment);
            fragmentManager.addToBackStack(null);
            fragmentManager.commit();

        }
    }
}
