package com.gmartdev.komsi.g_mart.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPesananModel {
    @SerializedName("code")
    String code;
    @SerializedName("result")
    List<PesananModel> result;
    @SerializedName("message")
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<PesananModel> getResult() {
        return result;
    }

    public void setResult(List<PesananModel> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
