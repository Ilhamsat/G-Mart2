package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProductCategoryModel {
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    List<ProductCategoryModel> result;

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

    public List<ProductCategoryModel> getResult() {
        return result;
    }

    public void setResult(List<ProductCategoryModel> result) {
        this.result = result;
    }
}
