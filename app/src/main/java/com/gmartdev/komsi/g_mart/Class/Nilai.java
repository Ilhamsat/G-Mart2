package com.gmartdev.komsi.g_mart.Class;

public class Nilai {
    private String id_produkkios;
    private String jumlah_pesan;
    private String harga;

    public Nilai(String id_produkkios, String jumlah_pesan, String harga) {
        this.id_produkkios = id_produkkios;
        this.jumlah_pesan = jumlah_pesan;
        this.harga= harga;
    }

    public Nilai() {

    }

    public String getId_produkkios() {
        return id_produkkios;
    }

    public String getJumlah_pesan() {
        return jumlah_pesan;
    }

    public String getHarga() {
        return harga;
    }

    public void setId_produkkios(String id_produkkios) {
        this.id_produkkios = id_produkkios;
    }

    public void setJumlah_pesan(String jumlah_pesan) {
        this.jumlah_pesan = jumlah_pesan;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
