package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

public class GetDistanceModel {
    @SerializedName("jarak")
    Double jarak;
    @SerializedName("satuan")
    String satuan;

    public Double getJarak() {
        return jarak;
    }

    public void setJarak(Double jarak) {
        this.jarak = jarak;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}
