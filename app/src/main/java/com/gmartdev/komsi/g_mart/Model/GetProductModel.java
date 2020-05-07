package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProductModel {
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    ArrayList<ProductModel> listDataProduk;

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

    public ArrayList<ProductModel> getListDataProduk() {
        return listDataProduk;
    }

    public void setListDataProduk(ArrayList<ProductModel> listDataProduk) {
        this.listDataProduk = listDataProduk;
    }
}
