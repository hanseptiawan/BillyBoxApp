package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShipment {

	@SerializedName("idShipment")
	@Expose
	private String idShipment;
	@SerializedName("idPayment")
	@Expose
	private String idPayment;
	@SerializedName("lokasi")
	@Expose
	private String lokasi;
	@SerializedName("waktu")
	@Expose
	private String waktu;

	public String getIdShipment() {
		return idShipment;
	}

	public void setIdShipment(String idShipment) {
		this.idShipment = idShipment;
	}

	public String getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(String idPayment) {
		this.idPayment = idPayment;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getWaktu() {
		return waktu;
	}

	public void setWaktu(String waktu) {
		this.waktu = waktu;
	}

}
