package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCartModel {
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    List<CartModel> result;

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

    public List<CartModel> getResult() {
        return result;
    }

    public void setResult(List<CartModel> result) {
        this.result = result;
    }
}
