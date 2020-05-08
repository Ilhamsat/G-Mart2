package com.gmartdev.komsi.g_mart.API;

import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetLoginModel;
import com.gmartdev.komsi.g_mart.Model.GetPesananModel;
import com.gmartdev.komsi.g_mart.Model.GetProductModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("konsumen/cek_registeredkonsumen.php")
    Call<GetConsumerModel> getRegisteredKonsumen(
            @Field("no_hp") String no_hp
    );

    @FormUrlEncoded
    @POST("konsumen/login.php")
    Call<GetLoginModel>loginKonsumen(
            @Field("no_hp") String no_hp,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("konsumen/set_newkonsumen.php")
    Call<GetConsumerModel> setNewConsumer(
            @Field("nama") String nama,
            @Field("no_hp") String no_hp,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("konsumen/update_konsumen_token.php")
    Call<GetConsumerModel> updateConsumerToken(
            @Field("id_konsumen") String id_konsumen,
            @Field("no_hp") String no_hp,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("produk/get_produk_kategori.php")
    Call<GetProductModel> getProductCategory(
            @Field("token") String token,
            @Field("id_kategori") String id_kios
    );

    @FormUrlEncoded
    @POST("konsumen/get_pesanan_menunggu.php")
    Call<GetPesananModel> getPesananMenunggu(
            @Field("id_konsumen") String id_konsumen,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("konsumen/get_pesanan_diproses.php")
    Call<GetPesananModel> getPesananDiproses(
            @Field("id_konsumen") String id_konsumen,
            @Field("token") String token
    );
}
