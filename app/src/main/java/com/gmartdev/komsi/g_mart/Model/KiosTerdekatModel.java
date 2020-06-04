package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

public class KiosTerdekatModel {
    @SerializedName("id_kios")
    private String id_kios;
    @SerializedName("nama_kios")
    private String nama_kios;
    @SerializedName("nama_pemilik")
    private String nama_pemilik;
    @SerializedName("email")
    private String email;
    @SerializedName("no_hp")
    private String no_hp;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("status_buka")
    private String status_buka;
    @SerializedName("rating")
    private String rating;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("latitude")
    private String latitude;

    public KiosTerdekatModel(String id_kios, String nama_kios, String no_hp, String alamat, String status_buka, String rating, String longitude, String latitude) {
        this.id_kios = id_kios;
        this.nama_kios = nama_kios;
        this.no_hp = no_hp;
        this.alamat = alamat;
        this.status_buka = status_buka;
        this.rating = rating;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId_kios() {
        return id_kios;
    }

    public void setId_kios(String id_kios) {
        this.id_kios = id_kios;
    }

    public String getNama_kios() {
        return nama_kios;
    }

    public void setNama_kios(String nama_kios) {
        this.nama_kios = nama_kios;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus_buka() {
        return status_buka;
    }

    public void setStatus_buka(String status_buka) {
        this.status_buka = status_buka;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
