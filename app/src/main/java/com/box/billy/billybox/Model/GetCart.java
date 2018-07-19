package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCart {

    @SerializedName("detailCartId")
    @Expose
    private String detailCartId;
    @SerializedName("cartid")
    @Expose
    private String cartid;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("imgSrc")
    @Expose
    private String imgSrc;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("mediaId")
    @Expose
    private String mediaId;
    @SerializedName("namaFile")
    @Expose
    private String namaFile;
    @SerializedName("lokasi")
    @Expose
    private String lokasi;
    @SerializedName("mediaUrl")
    @Expose
    private String mediaUrl;
    @SerializedName("upadetedAt")
    @Expose
    private String upadetedAt;

    public String getDetailCartId() {
        return detailCartId;
    }

    public void setDetailCartId(String detailCartId) {
        this.detailCartId = detailCartId;
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getUpadetedAt() {
        return upadetedAt;
    }

    public void setUpadetedAt(String upadetedAt) {
        this.upadetedAt = upadetedAt;
    }

}