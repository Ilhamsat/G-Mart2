package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

public class ProductCategoryModel {
    @SerializedName("id_produkkios")
    private String id_produkkios;
    @SerializedName("id_kios")
    private String id_kios;
    @SerializedName("id_produkkoperasi")
    private String id_produkkoperasi;
    @SerializedName("stok")
    private String stok;
    @SerializedName("harga")
    private String harga;
    @SerializedName("nama_produk")
    private String nama_produk;
    @SerializedName("merk")
    private String merk;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("nama_kategori")
    private String nama_kategori;
    @SerializedName("nama_kios")
    private String nama_kios;

    public ProductCategoryModel(String merk, String nama_produk, String harga, String nama_kios) {
        this.merk = merk;
        this.nama_produk = nama_produk;
        this.harga = harga;
        this.nama_kios = nama_kios;
    }

    public String getId_produkkios() {
        return id_produkkios;
    }

    public void setId_produkkios(String id_produkkios) {
        this.id_produkkios = id_produkkios;
    }

    public String getId_kios() {
        return id_kios;
    }

    public void setId_kios(String id_kios) {
        this.id_kios = id_kios;
    }

    public String getId_produkkoperasi() {
        return id_produkkoperasi;
    }

    public void setId_produkkoperasi(String id_produkkoperasi) {
        this.id_produkkoperasi = id_produkkoperasi;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getNama_kios() {
        return nama_kios;
    }

    public void setNama_kios(String nama_kios) {
        this.nama_kios = nama_kios;
    }
}
