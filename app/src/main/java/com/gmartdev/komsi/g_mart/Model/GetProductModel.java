package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetProductModel {
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    List<ProductModel> result;

    @SerializedName("lokasi_kios")
    String lokasi_kios;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductModel> getResult() {
        return result;
    }

    public void setResult(List<ProductModel> result) {
        this.result = result;
    }

    public String getLokasi_kios() {
        return lokasi_kios;
    }

    public void setLokasi_kios(String lokasi_kios) {
        this.lokasi_kios = lokasi_kios;
    }
}
