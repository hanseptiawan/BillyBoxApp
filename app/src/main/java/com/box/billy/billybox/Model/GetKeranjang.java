package com.box.billy.billybox.Model;

/**
 * Created by han on 7/18/2018.
 */

public class GetKeranjang {

    private String cartonid;
    private String name;
    private String ukuran;
    private String harga;

    public GetKeranjang(){
        super();
    }

    public GetKeranjang(String cartonid, String name, String ukuran, String harga) {
        this.cartonid = cartonid;
        this.name = name;
        this.ukuran = ukuran;
        this.harga = harga;
    }

    public String getCartonid() {
        return cartonid;
    }

    public void setCartonid(String cartonid) {
        this.cartonid = cartonid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    @Override
    public boolean equals(Object object){
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        GetKeranjang other = (GetKeranjang) object;
        if (cartonid != other.cartonid)
            return false;
        return true;
    }
}
