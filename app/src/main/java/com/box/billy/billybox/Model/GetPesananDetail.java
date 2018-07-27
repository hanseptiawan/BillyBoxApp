package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPesananDetail {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("metodePembayaran")
    @Expose
    private String metodePembayaran;
    @SerializedName("metodePengiriman")
    @Expose
    private String metodePengiriman;
    @SerializedName("tanggalPengantaran")
    @Expose
    private String tanggalPengantaran;
    @SerializedName("tanggalDiterima")
    @Expose
    private String tanggalDiterima;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("noTelp")
    @Expose
    private String noTelp;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public String getMetodePengiriman() {
        return metodePengiriman;
    }

    public void setMetodePengiriman(String metodePengiriman) {
        this.metodePengiriman = metodePengiriman;
    }

    public String getTanggalPengantaran() {
        return tanggalPengantaran;
    }

    public void setTanggalPengantaran(String tanggalPengantaran) {
        this.tanggalPengantaran = tanggalPengantaran;
    }

    public String getTanggalDiterima() {
        return tanggalDiterima;
    }

    public void setTanggalDiterima(String tanggalDiterima) {
        this.tanggalDiterima = tanggalDiterima;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
