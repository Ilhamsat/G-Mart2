package com.gmartdev.komsi.g_mart.Model;

public class TransactionBasketModel {

    private String howMany;
    private String items;
    private String totalPriceItems;
    private String delivery;

    public TransactionBasketModel(String howMany, String items, String totalPriceItems, String delivery) {
        this.howMany = howMany;
        this.items = items;
        this.totalPriceItems = totalPriceItems;
        this.delivery = delivery;
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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
