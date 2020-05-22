package com.gmartdev.komsi.g_mart.Model;

import com.gmartdev.komsi.g_mart.Class.Nilai;
import com.google.gson.JsonArray;
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
    @SerializedName("alamat_konsumen")
    private String alamat_konsumen;
    @SerializedName("nama_konsumen")
    private String nama_konsumen;
    @SerializedName("alamat_kios")
    private String alamat_kios;
    @SerializedName("nama_kios")
    private String nama_kios;
    @SerializedName("produk")
    List<ProductDetailPesananModel> produk;

    @SerializedName("total")
    private String total;

    @SerializedName("id_keranjang")
    private String id_keranjang;
    @SerializedName("cart")
    private String cart;
    @SerializedName("id_kios")
    private String id_kios;
    @SerializedName("id_pembayaran")
    private String id_pembayaran;
    @SerializedName("id_pengiriman")
    private String id_pengiriman;


    public PesananModel(String id_order, String subtotal_harga, String status, String metode_kirim, List<ProductDetailPesananModel> produk, String nama_kios, String alamat_konsumen, String total, String id_keranjang, String cart, String id_kios, String id_pembayaran, String id_pengiriman) {
        this.id_order = id_order;
        this.subtotal_harga = subtotal_harga;
        this.status = status;
        this.metode_kirim = metode_kirim;
        this.produk = produk;
        this.nama_kios = nama_kios;
        this.alamat_konsumen = alamat_konsumen;
        this.total = total;
        this.id_keranjang = id_keranjang;
        this.cart = cart;
        this.id_kios = id_kios;
        this.id_pembayaran = id_pembayaran;
        this.id_pengiriman = id_pengiriman;
    }

    public PesananModel(String id_order, String subtotal_harga, String status, String metode_kirim, List<ProductDetailPesananModel> produk, String nama_kios, String alamat_konsumen, String total) {
        this.id_order = id_order;
        this.subtotal_harga = subtotal_harga;
        this.status = status;
        this.metode_kirim = metode_kirim;
        this.produk = produk;
        this.nama_kios = nama_kios;
        this.alamat_konsumen = alamat_konsumen;
        this.total = total;
    }

    public PesananModel(String id_order, String subtotal_harga, String status, String metode_kirim, List<ProductDetailPesananModel> produk, String nama_kios, String alamat_konsumen) {
        this.id_order = id_order;
        this.subtotal_harga = subtotal_harga;
        this.status = status;
        this.metode_kirim = metode_kirim;
        this.produk = produk;
        this.nama_kios = nama_kios;
        this.alamat_konsumen = alamat_konsumen;
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

    public String getAlamat_kios() {
        return alamat_kios;
    }

    public void setAlamat_kios(String alamat_kios) {
        this.alamat_kios = alamat_kios;
    }

    public String getNama_kios() {
        return nama_kios;
    }

    public void setNama_kios(String nama_kios) {
        this.nama_kios = nama_kios;
    }

    public List<ProductDetailPesananModel> getProduk() {
        return produk;
    }

    public void setProduk(List<ProductDetailPesananModel> produk) {
        this.produk = produk;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(String id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getId_kios() {
        return id_kios;
    }

    public void setId_kios(String id_kios) {
        this.id_kios = id_kios;
    }

    public String getId_pembayaran() {
        return id_pembayaran;
    }

    public void setId_pembayaran(String id_pembayaran) {
        this.id_pembayaran = id_pembayaran;
    }

    public String getId_pengiriman() {
        return id_pengiriman;
    }

    public void setId_pengiriman(String id_pengiriman) {
        this.id_pengiriman = id_pengiriman;
    }
}
