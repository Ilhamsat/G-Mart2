package com.gmartdev.komsi.g_mart.API;

import android.renderscript.Sampler;

import com.gmartdev.komsi.g_mart.Class.Nilai;
import com.gmartdev.komsi.g_mart.Model.CartModel;
import com.gmartdev.komsi.g_mart.Model.GetCartModel;
import com.gmartdev.komsi.g_mart.Model.GetConsumerModel;
import com.gmartdev.komsi.g_mart.Model.GetLoginModel;
import com.gmartdev.komsi.g_mart.Model.GetPesananModel;
import com.gmartdev.komsi.g_mart.Model.GetProduckPesananDetailModel;
import com.gmartdev.komsi.g_mart.Model.GetProductCategoryModel;
import com.gmartdev.komsi.g_mart.Model.GetProductModel;
import com.here.posclient.PositionEstimate;

import org.json.JSONArray;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("konsumen/update_konsumen.php")
    Call<GetConsumerModel> updateConsumer(
            @Field("id_konsumen") String id_konsumen,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("token") String token,
            @Field("tanggal_lahir") String tanggal_lahir
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
    @POST("produk/get_keranjang.php")
    Call<GetPesananModel> getPesananKeranjang(
            @Field("token") String token,
            @Field("id_konsumen") String id_konsumen

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

    @FormUrlEncoded
    @POST("konsumen/get_pesanan_riwayat.php")
    Call<GetPesananModel> getPesananRiwayat(
            @Field("token") String token,
            @Field("id_konsumen") String id_konsumen
    );

    @FormUrlEncoded
    @POST("produk/get_produk_kategori.php")
    Call<GetProductCategoryModel> getProductCategory(
            @Field("token") String token,
            @Field("id_kategori") String id_kategori
    );

    @FormUrlEncoded
    @POST("produk/get_produk_kios.php")
    Call<GetProductModel> getProdukKios(
            @Field("id_kios") String id_kios,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("produk/get_pesanan_detail.php")
    Call<GetProduckPesananDetailModel> getProdukPesananDetail(
            @Field("id_order") String id_order,
            @Field("token") String token
    );

//    @Multipart
//    @POST("produk/post_create_keranjang.php")
//    Call<GetCartModel> setNewKeranjang(
//            @Part("id_pembayaran") String id_pembayaran,
//            @Part("id_pengiriman") String id_pengiriman,
//            @Part("id_kios") String id_kios,
//            @Part("id_konsumen") String id_konsumen,
//            @Body JSONArray cart,
//            @Part("token") String token
//    );

    @FormUrlEncoded
    @POST("produk/post_create_keranjang.php")
    Call<GetCartModel> setNewKeranjang(
            @Field("id_pembayaran") String id_pembayaran,
            @Field("id_pengiriman") String id_pengiriman,
            @Field("id_kios") String id_kios,
            @Field("id_konsumen") String id_konsumen,
            @Field("cart") JSONArray cart,
//            @FieldMap Map<String, String> cart,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("produk/delete_keranjang.php")
    Call<GetCartModel> deleteKeranjang(
        @Field("token") String token_konsumen,
        @Field("id_konsumen") String id_konsumen,
        @Field("id_keranjang") String id_keranjang
    );

    @FormUrlEncoded
    @POST("konsumen/get_detail_keranjang.php")
    Call<GetProduckPesananDetailModel> getProdukPesananDetailKeranjang(
            @Field("token") String token,
            @Field("id_konsumen") String id_konsumen,
            @Field("id_keranjang") String id_keranjang
    );

    @FormUrlEncoded
    @POST("konsumen/update_detail_keranjang.php")
    Call<GetCartModel> updateKeranjang(
            @Field("token") String token,
            @Field("id_konsumen") String id_konsumen,
            @Field("id_keranjang") String id_keranjang,
            @Field("cart") JSONArray cart,
            @Field("id_pengiriman") String id_pengiriman,
            @Field("total") String total
    );

    @FormUrlEncoded
    @POST("produk/create_pesanan.php")
    Call<GetCartModel> setNewOrder(
            @Field("id_pembayaran") String id_pembayaran,
            @Field("id_pengiriman") String id_pengiriman,
            @Field("id_kios") String id_kios,
            @Field("id_konsumen") String id_konsumen,
            @Field("cart") JSONArray cart,
            @Field("token") String token
    );

}
