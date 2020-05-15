package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetProductModel {
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    List<ProductModel> result;

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

    public List<ProductModel> getResult() {
        return result;
    }

    public void setResult(List<ProductModel> result) {
        this.result = result;
    }
}
