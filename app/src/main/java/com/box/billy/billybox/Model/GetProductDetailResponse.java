package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProductDetailResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("dataBody")
    @Expose
    private DataBodyProduct dataBody;

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

    public DataBodyProduct getDataBody() {
        return dataBody;
    }

    public void setDataBody(DataBodyProduct dataBody) {
        this.dataBody = dataBody;
    }

}
