package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetKeranjangPesananResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("dataBody")
    @Expose
    private List<GetKeranjangPesanan> dataBody = null;

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

    public List<GetKeranjangPesanan> getDataBody() {
        return dataBody;
    }

    public void setDataBody(List<GetKeranjangPesanan> dataBody) {
        this.dataBody = dataBody;
    }

}
