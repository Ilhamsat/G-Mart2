package com.gmartdev.komsi.g_mart.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("konsumen/set_newkonsumen.php")
    Call<ResponseBody> setNewKonsumen(
            @Field("id_konsumen") String id_konsumen,
            @Field("nama") String nama,
            @Field("no_hp") String no_hp,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("token") String token

    );
}
