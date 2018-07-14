package com.box.billy.billybox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatabodyPesanan {

    @SerializedName("0")
    @Expose
    private com.box.billy.billybox.Model.GetPesananDetail _0;

    public com.box.billy.billybox.Model.GetPesananDetail get0() {
        return _0;
    }

    public void set0(com.box.billy.billybox.Model.GetPesananDetail _0) {
        this._0 = _0;
    }

}
