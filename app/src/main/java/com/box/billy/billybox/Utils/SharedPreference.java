package com.box.billy.billybox.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.box.billy.billybox.Model.GetKeranjang;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by han on 7/17/2018.
 */

public class SharedPreference {

    private static final String PREF_PRODUCTS = "PRODUCT_APP";
    private static final String FAVORITE = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    public void saveFavorite(Context context, List<GetKeranjang> favorites){
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREF_PRODUCTS,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonproducts = gson.toJson(favorites);
        editor.putString(FAVORITE, jsonproducts);
        editor.commit();
        Log.d(TAG, "save order");
    }

    public void addFavorite(Context context, GetKeranjang product){
        List<GetKeranjang> favorites = getFavorite(context);
        if (favorites == null)
            favorites = new ArrayList<GetKeranjang>();
        favorites.add(product);
        saveFavorite(context, favorites);
    }

    public void removeFavorite(Context context, GetKeranjang product){
        ArrayList<GetKeranjang> favorites = getFavorite(context);
        if (favorites != null){
            favorites.remove(product);
            saveFavorite(context, favorites);
        }
    }

    public ArrayList<GetKeranjang> getFavorite(Context context) {
        SharedPreferences settings;
        List<GetKeranjang> favorites;

        settings = context.getSharedPreferences(PREF_PRODUCTS,
                Context.MODE_PRIVATE);

        if(settings.contains(FAVORITE)){
            String jsonFavorite = settings.getString(FAVORITE, null);
            Gson gson = new Gson();
            GetKeranjang[] favoriteItems = gson.fromJson(jsonFavorite,
                    GetKeranjang[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<>(favorites);
        } else
            return null;

        return (ArrayList<GetKeranjang>) favorites;
    }
}
