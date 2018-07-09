package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductCat {

    @SerializedName("categoryCartonId")
    @Expose
    private String categoryCartonId;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("warna")
    @Expose
    private String warna;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("upadtedAt")
    @Expose
    private String upadtedAt;

    public String getCategoryCartonId() {
        return categoryCartonId;
    }

    public void setCategoryCartonId(String categoryCartonId) {
        this.categoryCartonId = categoryCartonId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpadtedAt() {
        return upadtedAt;
    }

    public void setUpadtedAt(String upadtedAt) {
        this.upadtedAt = upadtedAt;
    }

}
