package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetKeranjangPesanan {

    @SerializedName("orderDetailId")
    @Expose
    private String orderDetailId;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("cartonId")
    @Expose
    private String cartonId;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("totalHarga")
    @Expose
    private String totalHarga;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
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
    @SerializedName("imgSrc")
    @Expose
    private String imgSrc;

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCartonId() {
        return cartonId;
    }

    public void setCartonId(String cartonId) {
        this.cartonId = cartonId;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

}
