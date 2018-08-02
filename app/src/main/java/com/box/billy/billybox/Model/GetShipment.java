package com.box.billy.billybox.Model;

public class GetShipment {
	private String idPayment;
	private String lokasi;
	private String waktu;
	private String idShipment;

	public void setIdPayment(String idPayment){
		this.idPayment = idPayment;
	}

	public String getIdPayment(){
		return idPayment;
	}

	public void setLokasi(String lokasi){
		this.lokasi = lokasi;
	}

	public String getLokasi(){
		return lokasi;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setIdShipment(String idShipment){
		this.idShipment = idShipment;
	}

	public String getIdShipment(){
		return idShipment;
	}

	@Override
 	public String toString(){
		return 
			"GetShipment{" +
			"idPayment = '" + idPayment + '\'' + 
			",lokasi = '" + lokasi + '\'' + 
			",waktu = '" + waktu + '\'' + 
			",idShipment = '" + idShipment + '\'' + 
			"}";
		}
}
