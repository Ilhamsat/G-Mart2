package com.gmartdev.komsi.g_mart.Class;

public class Nilai {
    private String id_produkkios;
    private String jumlah;
    private String harga;

    public Nilai(String id_produkkios, String jumlah, String harga) {
        this.id_produkkios = id_produkkios;
        this.jumlah = jumlah;
        this.harga= harga;
    }

    public Nilai() {

    }

    public String getId_produkkios() {
        return id_produkkios;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public void setId_produkkios(String id_produkkios) {
        this.id_produkkios = id_produkkios;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
