package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetConsumerModel {

    @SerializedName("code")
    String code;
    @SerializedName("result")
    List<ConsumerModel> listDataKonsumen;
    @SerializedName("message")
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ConsumerModel> getListDataKonsumen() {
        return listDataKonsumen;
    }

    public void setListDataKonsumen(List<ConsumerModel> listDataKonsumen) {
        this.listDataKonsumen = listDataKonsumen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
