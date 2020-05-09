package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

public class ProductDetailPesananModel {
    @SerializedName("merk")
    private String merk;
    @SerializedName("nama_produk")
    private String nama_produk;


    public ProductDetailPesananModel(String merk, String nama_produk) {
        this.merk = merk;
        this.nama_produk = nama_produk;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }
}
