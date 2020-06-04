package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.DetailPesananRiwayatAdapter;
import com.gmartdev.komsi.g_mart.Model.DetailPesananRiwayatModel;
import com.gmartdev.komsi.g_mart.Model.GetProduckPesananDetailModel;
import com.gmartdev.komsi.g_mart.Model.ItemAtStoreModel;
import com.gmartdev.komsi.g_mart.Model.ProductPesananDetailModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class DetailPesananRiwayatActivity extends AppCompatActivity {

    private static final String TAG = "DetailRiwayat";

    String nama_kios, alamat_konsumen, id_order, token_konsumen, id_konsumen;
    float ratingPenilaian = 0;

    TextView textStoreName, alamatPengiriman;
    MaterialButton simpanRating;
    RatingBar ratingBar;
    RecyclerView recyclerView;

    List<ProductPesananDetailModel> mList = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_riwayat);

        id_order = getIntent().getStringExtra("id_order");
        nama_kios = getIntent().getStringExtra("nama_kios");
        alamat_konsumen = getIntent().getStringExtra("alamat_konsumen");

        textStoreName = (TextView) findViewById(R.id.textStoreName);
        alamatPengiriman = (TextView) findViewById(R.id.alamatPengiriman);

        ratingBar = (RatingBar) findViewById(R.id.ratingPenilaianToko);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rating = (int) v;
                ratingPenilaian = ratingBar.getRating();
            }
        });

        simpanRating = (MaterialButton) findViewById(R.id.simpanRating);
        simpanRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: rating dari pesanan ini = " + ratingPenilaian);
            }
        });

        textStoreName.setText(nama_kios);
        alamatPengiriman.setText(alamat_konsumen);

//        mList = new ArrayList<>();
//        mList.add(new DetailPesananRiwayatModel("Beras", "25000", "2", "50000"));
//        mList.add(new DetailPesananRiwayatModel("Rokok Djarum 12", "15000", "1", "15000"));
//        mList.add(new DetailPesananRiwayatModel("Sabun Lifebouy", "2000", "3", "6000"));
        recyclerView = (RecyclerView) findViewById(R.id.listPesananRiwayat);
        callApi();

    }

    private void callApi(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_order = getIntent().getStringExtra("id_order");
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetProduckPesananDetailModel> call = api.getProdukPesananDetail(id_order, token_konsumen);
        call.enqueue(new Callback<GetProduckPesananDetailModel>() {
            @Override
            public void onResponse(Call<GetProduckPesananDetailModel> call, Response<GetProduckPesananDetailModel> response) {
                if(response.body().getResult() != null){
                    List<ProductPesananDetailModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    for (ProductPesananDetailModel productPesananDetailModel : list){
                        mList.add(new ProductPesananDetailModel( productPesananDetailModel.getNama_produk(), productPesananDetailModel.getMerk(), productPesananDetailModel.getHarga(), productPesananDetailModel.getJumlah_pesan()));
                    }

                    DetailPesananRiwayatAdapter detailPesananRiwayatAdapter = new DetailPesananRiwayatAdapter(DetailPesananRiwayatActivity.this, mList);
                    recyclerView.setAdapter(detailPesananRiwayatAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DetailPesananRiwayatActivity.this));
                }
            }

            @Override
            public void onFailure(Call<GetProduckPesananDetailModel> call, Throwable t) {

            }
        });

    }
}

