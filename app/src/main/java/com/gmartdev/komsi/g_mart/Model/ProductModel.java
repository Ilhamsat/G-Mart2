package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("id_produkkoperasi")
    private String id_produkkoperasi;
    @SerializedName("nama_produk")
    private String nama_produk;
    @SerializedName("merk")
    private String merk;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("nama_kategori")
    private String nama_kategori;
    @SerializedName("id_produkkios")
    private String id_produkkios;
    @SerializedName("harga")
    private String harga;
    @SerializedName("stok")
    private String stok;
    @SerializedName("nama_kios")
    private String nama_kios;
    @SerializedName("id_kios")
    private String id_kios;
    @SerializedName("alamat_kios")
    private String alamat_kios;

    public ProductModel() {

    }

    public ProductModel(String id_produkkoperasi, String nama_produk, String merk, String gambar, String nama_kategori, String id_produkkios, String harga, String stok, String nama_kios, String id_kios, String alamat_kios) {
        this.id_produkkoperasi = id_produkkoperasi;
        this.nama_produk = nama_produk;
        this.merk = merk;
        this.gambar = gambar;
        this.nama_kategori = nama_kategori;
        this.id_produkkios = id_produkkios;
        this.harga = harga;
        this.stok = stok;
        this.nama_kios = nama_kios;
        this.id_kios = id_kios;
        this.alamat_kios = alamat_kios;
    }

    public String getId_produkkoperasi() {
        return id_produkkoperasi;
    }

    public void setId_produkkoperasi(String id_produkkoperasi) {
        this.id_produkkoperasi = id_produkkoperasi;
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

    public String getId_produkkios() {
        return id_produkkios;
    }

    public void setId_produkkios(String id_produkkios) {
        this.id_produkkios = id_produkkios;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getNama_kios() {
        return nama_kios;
    }

    public void setNama_kios(String nama_kios) {
        this.nama_kios = nama_kios;
    }

    public String getId_kios() {
        return id_kios;
    }

    public void setId_kios(String id_kios) {
        this.id_kios = id_kios;
    }

    public String getAlamat_kios() {
        return alamat_kios;
    }

    public void setAlamat_kios(String alamat_kios) {
        this.alamat_kios = alamat_kios;
    }
}
