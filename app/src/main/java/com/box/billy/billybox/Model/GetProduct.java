package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProduct {

    @SerializedName("cartonId")
    @Expose
    private String cartonId;
    @SerializedName("namaItem")
    @Expose
    private String namaItem;
    @SerializedName("categoryCartonId")
    @Expose
    private String categoryCartonId;
    @SerializedName("stok")
    @Expose
    private String stok;
    @SerializedName("hargaBeli")
    @Expose
    private String hargaBeli;
    @SerializedName("hargaJual")
    @Expose
    private String hargaJual;
    @SerializedName("ukuran")
    @Expose
    private String ukuran;
    @SerializedName("grametur")
    @Expose
    private String grametur;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("imgSrc")
    @Expose
    private String imgSrc;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("warna")
    @Expose
    private String warna;
    @SerializedName("upadtedAt")
    @Expose
    private String upadtedAt;

    public String getCartonId() {
        return cartonId;
    }

    public void setCartonId(String cartonId) {
        this.cartonId = cartonId;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public String getCategoryCartonId() {
        return categoryCartonId;
    }

    public void setCategoryCartonId(String categoryCartonId) {
        this.categoryCartonId = categoryCartonId;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getGrametur() {
        return grametur;
    }

    public void setGrametur(String grametur) {
        this.grametur = grametur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getUpadtedAt() {
        return upadtedAt;
    }

    public void setUpadtedAt(String upadtedAt) {
        this.upadtedAt = upadtedAt;
    }

}
