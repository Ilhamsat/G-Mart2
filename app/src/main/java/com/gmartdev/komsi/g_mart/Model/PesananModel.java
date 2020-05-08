package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PesananModel {
    @SerializedName("id_order")
    private String id_order;
    @SerializedName("subtotal_harga")
    private String subtotal_harga;
    @SerializedName("subtotal_harga_beli")
    private String subtotal_harga_beli;
    @SerializedName("status")
    private String status;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("waktu")
    private String waktu;
    @SerializedName("metode_bayar")
    private String metode_bayar;
    @SerializedName("metode_kirim")
    private String metode_kirim;
    @SerializedName("biaya_kirim")
    private String biaya_kirim;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("nama")
    private String nama;
    @SerializedName("produk")
    List<ProductDetailPesananModel> produk;

    public PesananModel(String id_order, String subtotal_harga, String status, String metode_kirim) {
        this.id_order = id_order;
        this.subtotal_harga = subtotal_harga;
        this.status = status;
        this.metode_kirim = metode_kirim;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getSubtotal_harga() {
        return subtotal_harga;
    }

    public void setSubtotal_harga(String subtotal_harga) {
        this.subtotal_harga = subtotal_harga;
    }

    public String getSubtotal_harga_beli() {
        return subtotal_harga_beli;
    }

    public void setSubtotal_harga_beli(String subtotal_harga_beli) {
        this.subtotal_harga_beli = subtotal_harga_beli;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getMetode_bayar() {
        return metode_bayar;
    }

    public void setMetode_bayar(String metode_bayar) {
        this.metode_bayar = metode_bayar;
    }

    public String getMetode_kirim() {
        return metode_kirim;
    }

    public void setMetode_kirim(String metode_kirim) {
        this.metode_kirim = metode_kirim;
    }

    public String getBiaya_kirim() {
        return biaya_kirim;
    }

    public void setBiaya_kirim(String biaya_kirim) {
        this.biaya_kirim = biaya_kirim;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<ProductDetailPesananModel> getProduk() {
        return produk;
    }

    public void setProduk(List<ProductDetailPesananModel> produk) {
        this.produk = produk;
    }
}
