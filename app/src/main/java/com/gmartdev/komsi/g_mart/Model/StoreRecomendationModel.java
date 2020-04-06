package com.gmartdev.komsi.g_mart.Model;

public class StoreRecomendationModel {
    private String storeName;
    private String distance;

    public StoreRecomendationModel() {
    }

    public StoreRecomendationModel(String storeName, String distance) {
        this.storeName = storeName;
        this.distance = distance;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
