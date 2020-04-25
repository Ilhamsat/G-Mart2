package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

public class ConsumerModel {

    @SerializedName("id_konsumen")
    private String id_konsumen;
    @SerializedName("nama")
    private String nama;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("email")
    private String email;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("rating")
    private String rating;

    public ConsumerModel(){

    }

    public ConsumerModel(String id_konsumen, String nama, String no_hp, String email, String alamat, String rating) {
        this.id_konsumen = id_konsumen;
        this.nama = nama;
        this.no_hp = no_hp;
        this.email = email;
        this.alamat = alamat;
        this.rating = rating;
    }

    public String getId_konsumen() {
        return id_konsumen;
    }

    public void setId_konsumen(String id_konsumen) {
        this.id_konsumen = id_konsumen;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
