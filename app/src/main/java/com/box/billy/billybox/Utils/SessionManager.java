package com.box.billy.billybox.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.box.billy.billybox.Main.Main;

import java.util.HashMap;

@SuppressLint("CommitPrefEdits")
public class SessionManager {
    //shared preferences
    SharedPreferences sharedPreferences;
    Editor editor;
    Context _context;
    private static String TAG = SessionManager.class.getSimpleName();

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "Session";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_ORDER = "IsOrderIn";

    public final String KEY_ID = "userID";
    public final String KEY_FNAME = "firstName";
    public final String KEY_LNAME = "lastName";
    public final String KEY_USERNAME = "username";
    public final String KEY_PASSWORD = "password";
    public final String KEY_IMG = "imgSrc";
    public final String KEY_ADDR = "alamat";
    public final String KEY_PHONE = "noTelp";
    public final String KEY_TTL = "tglLahir";
    public final String KEY_CARTID = "cartID";
    public final String KEY_IMGBASE64 = "imgbase64";

    //constructor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(IS_LOGIN, isLoggedIn);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    //    create login session
    public void createLoginSession(String userid, String firstName,String lastName,
                               String username, String password, String alamat,
                               String noTelp,String tglLahir, String img){
        //storing login value as true
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, userid);
        editor.putString(KEY_FNAME, firstName);
        editor.putString(KEY_LNAME, lastName);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_IMG, img);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_ADDR, alamat);
        editor.putString(KEY_PHONE, noTelp);
        editor.putString(KEY_TTL, tglLahir);

        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void updateUserSession(String fname, String lname,
                                  String username, String password,
                                  String alamat, String notelp,
                                  String ttl){
        editor.putString(KEY_FNAME, fname);
        editor.putString(KEY_LNAME, lname);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_ADDR, alamat);
        editor.putString(KEY_PHONE, notelp);
        editor.putString(KEY_TTL, ttl);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, Main.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
            return true;
        }
        return false;
    }

    public boolean checkAuthorization(){
        if(!this.isLoggedIn()){
            return true;
        } else {
            return false;
        }
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, sharedPreferences.getString(KEY_ID, null));
        user.put(KEY_FNAME, sharedPreferences.getString(KEY_FNAME, null));
        user.put(KEY_LNAME, sharedPreferences.getString(KEY_LNAME, null));
        user.put(KEY_IMG, sharedPreferences.getString(KEY_IMG, null));
        user.put(KEY_USERNAME, sharedPreferences.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));
        user.put(KEY_ADDR, sharedPreferences.getString(KEY_ADDR, null));
        user.put(KEY_PHONE, sharedPreferences.getString(KEY_PHONE, null));
        user.put(KEY_TTL, sharedPreferences.getString(KEY_TTL, null));

        return user;
    }

    public void createImg(String img){
        editor.putString(KEY_IMGBASE64, img);
        editor.commit();

        Log.d("img created : ", img);
    }

    public HashMap<String, String> getImg(){
        HashMap<String, String> cartID = new HashMap<String, String>();
        cartID.put(KEY_IMGBASE64, sharedPreferences.getString(KEY_IMGBASE64, null));

        return cartID;
    }

    public void createCartID(String cartID){
        //storing login value as true
        editor.putBoolean(IS_ORDER, false);
        editor.putString(KEY_CARTID, cartID);

        editor.commit();
        Log.d(TAG, "Cart ID added");
    }

    public HashMap<String, String> getCartID(){
        HashMap<String, String> cartID = new HashMap<String, String>();
        cartID.put(KEY_CARTID, sharedPreferences.getString(KEY_CARTID, null));

        return cartID;
    }

    public boolean orderCommit(String cartID){
        if(!this.isOrderSend()){
            deleteCartID(cartID);
            return true;
        }
        return false;
    }

    public void deleteCartID(String cartID){
        if (cartID != null){
            sharedPreferences.edit()
                    .remove(cartID)
                    .apply();
        }
    }

    public boolean checkOrderCommit(){
        if(!this.isOrderSend()){
            return true;
        }
        return false;
    }

    public void logoutUser(){
        if (!this.checkOrderCommit()){
            editor.clear();
            editor.commit();
        }else {
            editor.putBoolean(IS_LOGIN, false);
            Intent i = new Intent(_context, Main.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public boolean isOrderSend(){
        return sharedPreferences.getBoolean(IS_ORDER, false);
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
