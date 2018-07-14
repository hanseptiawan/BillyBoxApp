package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPesanan {

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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("namaDepan")
    @Expose
    private String namaDepan;
    @SerializedName("namaBelakang")
    @Expose
    private String namaBelakang;
    @SerializedName("imgSrc")
    @Expose
    private String imgSrc;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("noTelp")
    @Expose
    private String noTelp;
    @SerializedName("tglLahir")
    @Expose
    private String tglLahir;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

}
