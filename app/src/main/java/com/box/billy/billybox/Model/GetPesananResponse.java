package com.box.billy.billybox.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPesananResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("dataBody")
    @Expose
    private List<GetPesanan> dataBody = null;

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

    public List<GetPesanan> getDataBody() {
        return dataBody;
    }

    public void setDataBody(List<GetPesanan> dataBody) {
        this.dataBody = dataBody;
    }

}
