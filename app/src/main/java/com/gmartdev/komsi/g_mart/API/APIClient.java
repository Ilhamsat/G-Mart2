package com.gmartdev.komsi.g_mart.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "http://gmart.vokasidev.com/api/";
    private static APIClient mInstance;
    private Retrofit retrofit;

    private APIClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized APIClient getInstance(){
        if (mInstance == null){
            mInstance = new APIClient();
        }
        return mInstance;
    }

    public API getAPI(){
        return retrofit.create(API.class);
    }


}
