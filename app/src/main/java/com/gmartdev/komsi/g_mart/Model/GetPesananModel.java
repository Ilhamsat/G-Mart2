package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPesananModel {
    @SerializedName("code")
    String code;
    @SerializedName("result")
    List<PesananModel> listPesanan;
    @SerializedName("message")
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<PesananModel> getListPesanan() {
        return listPesanan;
    }

    public void setListPesanan(List<PesananModel> listPesanan) {
        this.listPesanan = listPesanan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
