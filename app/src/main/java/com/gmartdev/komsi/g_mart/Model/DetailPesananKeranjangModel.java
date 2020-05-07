package com.gmartdev.komsi.g_mart.Model;

public class DetailPesananKeranjangModel {

    private String itemName;
    private String itemPrice;
    private String totalItem;
    private String totalPrice;

    public DetailPesananKeranjangModel(String itemName, String itemPrice, String totalItem, String totalPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.totalItem = totalItem;
        this.totalPrice = totalPrice;
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

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
