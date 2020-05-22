package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartModel {
    @SerializedName("id_keranjang")
    private String id_keranjang;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("waktu")
    private String waktu;
    @SerializedName("cart")
    private JsonArray cart;
    @SerializedName("total")
    private String total;
    @SerializedName("metode_bayar")
    private String metode_bayar;
    @SerializedName("metode_kirim")
    private String metode_kirim;
    @SerializedName("biaya_kirim")
    private String biaya_kirim;
    @SerializedName("alamat_konsumen")
    private String alamat_konsumen;
    @SerializedName("nama_konsumen")
    private String nama_konsumen;
    @SerializedName("nama_kios")
    private String nama_kios;
    @SerializedName("alamat_kios")
    private String alamat_kios;
    @SerializedName("produk")
    List<ProductDetailPesananModel> produk;

    public CartModel(String id_keranjang, String tanggal, String waktu, JsonArray cart, String total, String metode_bayar, String metode_kirim, String biaya_kirim, String alamat_konsumen, String nama_konsumen, String nama_kios, String alamat_kios, List<ProductDetailPesananModel> produk) {
        this.id_keranjang = id_keranjang;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.cart = cart;
        this.total = total;
        this.metode_bayar = metode_bayar;
        this.metode_kirim = metode_kirim;
        this.biaya_kirim = biaya_kirim;
        this.alamat_konsumen = alamat_konsumen;
        this.nama_konsumen = nama_konsumen;
        this.nama_kios = nama_kios;
        this.alamat_kios = alamat_kios;
        this.produk = produk;
    }

    public String getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(String id_keranjang) {
        this.id_keranjang = id_keranjang;
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

    public JsonArray getCart() {
        return cart;
    }

    public void setCart(JsonArray cart) {
        this.cart = cart;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getAlamat_konsumen() {
        return alamat_konsumen;
    }

    public void setAlamat_konsumen(String alamat_konsumen) {
        this.alamat_konsumen = alamat_konsumen;
    }

    public String getNama_konsumen() {
        return nama_konsumen;
    }

    public void setNama_konsumen(String nama_konsumen) {
        this.nama_konsumen = nama_konsumen;
    }

    public String getNama_kios() {
        return nama_kios;
    }

    public void setNama_kios(String nama_kios) {
        this.nama_kios = nama_kios;
    }

    public String getAlamat_kios() {
        return alamat_kios;
    }

    public void setAlamat_kios(String alamat_kios) {
        this.alamat_kios = alamat_kios;
    }

    public List<ProductDetailPesananModel> getProduk() {
        return produk;
    }

    public void setProduk(List<ProductDetailPesananModel> produk) {
        this.produk = produk;
    }
}
