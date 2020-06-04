package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetKiosTerdekatModel {
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<KiosTerdekatModel> data;

    public GetKiosTerdekatModel(String code, String message, List<KiosTerdekatModel> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<KiosTerdekatModel> getData() {
        return data;
    }

    public void setData(List<KiosTerdekatModel> data) {
        this.data = data;
    }
}
