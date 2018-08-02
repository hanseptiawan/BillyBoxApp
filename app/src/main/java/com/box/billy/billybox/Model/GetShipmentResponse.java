package com.box.billy.billybox.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetShipmentResponse {

	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("code")
	@Expose
	private Integer code;
	@SerializedName("dataBody")
	@Expose
	private List<GetShipment> dataBody = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<GetShipment> getDataBody() {
		return dataBody;
	}

	public void setDataBody(List<GetShipment> dataBody) {
		this.dataBody = dataBody;
	}

}