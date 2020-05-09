package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

public class ProductPesananDetailModel {
    @SerializedName("nama_produk")
    private String nama_produk;
    @SerializedName("merk")
    private String merk;
    @SerializedName("harga")
    private String harga;
    @SerializedName("jumlah_pesan")
    private String jumlah_pesan;

    public ProductPesananDetailModel(String nama_produk, String merk, String harga, String jumlah_pesan) {
        this.nama_produk = nama_produk;
        this.merk = merk;
        this.harga = harga;
        this.jumlah_pesan = jumlah_pesan;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah_pesan() {
        return jumlah_pesan;
    }

    public void setJumlah_pesan(String jumlah_pesan) {
        this.jumlah_pesan = jumlah_pesan;
    }
}
