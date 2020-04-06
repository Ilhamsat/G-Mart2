package com.gmartdev.komsi.g_mart.Model;

public class TransactionHistoryModel {
    private String items;
    private String totalPriceItems;
    private String status;
    private Integer successOrNot;

    public TransactionHistoryModel(){

    }

    public TransactionHistoryModel(String items, String totalPriceItems, String status, Integer successOrNot) {
        this.items = items;
        this.totalPriceItems = totalPriceItems;
        this.status = status;
        this.successOrNot = successOrNot;
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

    public Integer getSuccessOrNot() {
        return successOrNot;
    }

    public void setSuccessOrNot(Integer successOrNot) {
        this.successOrNot = successOrNot;
    }



}
