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

    @SerializedName("id_produkkios")
    private String id_produkkios;
    @SerializedName("gambar")
    private String gambar;

    //keranjang
    public ProductPesananDetailModel(String nama_produk, String merk, String harga, String jumlah_pesan, String id_produkkios, String gambar) {
        this.nama_produk = nama_produk;
        this.merk = merk;
        this.harga = harga;
        this.jumlah_pesan = jumlah_pesan;
        this.id_produkkios = id_produkkios;
        this.gambar = gambar;
    }

    //riwayat
    public ProductPesananDetailModel(String nama_produk, String merk, String harga, String jumlah_pesan, String gambar) {
        this.nama_produk = nama_produk;
        this.merk = merk;
        this.harga = harga;
        this.jumlah_pesan = jumlah_pesan;
        this.gambar = gambar;
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

    public String getId_produkkios() {
        return id_produkkios;
    }

    public void setId_produkkios(String id_produkkios) {
        this.id_produkkios = id_produkkios;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
