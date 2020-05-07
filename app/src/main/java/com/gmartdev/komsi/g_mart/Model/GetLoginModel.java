package com.gmartdev.komsi.g_mart.Model;

import android.renderscript.Sampler;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;

public class GetLoginModel {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    private LoginModel data;

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

    public LoginModel getData() {
        return data;
    }

    public void setData(LoginModel data) {
        this.data = data;
    }
}
