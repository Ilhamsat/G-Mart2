package com.gmartdev.komsi.g_mart.API;

import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("konsumen/set_newkonsumen.php")
    Call<GetConsumerModel> setNewConsumer(
            @Field("id_konsumen") String id_konsumen,
            @Field("nama") String nama,
            @Field("no_hp") String no_hp,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("konsumen/update_konsumen_token.php")
    Call<GetConsumerModel> updateConsumerToken(
            @Field("id_konsumen") String id_konsumen,
            @Field("no_hp") String no_hp,
            @Field("token") String token
    );
}
