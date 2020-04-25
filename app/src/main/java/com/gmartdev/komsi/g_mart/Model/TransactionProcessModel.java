package com.gmartdev.komsi.g_mart.Model;

public class TransactionProcessModel {

    private String howMany;
    private String items;
    private String totalPriceItems;
    private String status;

    public TransactionProcessModel(String howMany, String items, String totalPriceItems, String status) {
        this.howMany = howMany;
        this.items = items;
        this.totalPriceItems = totalPriceItems;
        this.status = status;
    }

    public String getHowMany() {
        return howMany;
    }

    public void setHowMany(String howMany) {
        this.howMany = howMany;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getTotalPriceItems() {
        return totalPriceItems;
    }

    public void setTotalPriceItems(String totalPriceItems) {
        this.totalPriceItems = totalPriceItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
