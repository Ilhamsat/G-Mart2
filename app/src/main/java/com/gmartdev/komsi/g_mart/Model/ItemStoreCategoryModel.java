package com.gmartdev.komsi.g_mart.Model;

public class ItemStoreCategoryModel {
    private String itemName;
    private String itemPrice;
    private String storeName;
    private String storeDistance;

    public ItemStoreCategoryModel() {
        super();
    }

    public ItemStoreCategoryModel(String itemName, String itemPrice, String storeName, String storeDistance) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.storeName = storeName;
        this.storeDistance = storeDistance;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDistance() {
        return storeDistance;
    }

    public void setStoreDistance(String storeDistance) {
        this.storeDistance = storeDistance;
    }
}
