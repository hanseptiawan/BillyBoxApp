package com.box.billy.billybox.Model;

import java.util.List;

public class GetShipmentResponse{
	private int code;
	private List<GetShipment> dataBody;
	private String status;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setDataBody(List<GetShipment> dataBody){
		this.dataBody = dataBody;
	}

	public List<GetShipment> getDataBody(){
		return dataBody;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"GetShipmentResponse{" + 
			"code = '" + code + '\'' + 
			",dataBody = '" + dataBody + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}