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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.DetailPesananKeranjangAdapter;
import com.gmartdev.komsi.g_mart.Model.DetailPesananKeranjangModel;
import com.gmartdev.komsi.g_mart.Model.GetProduckPesananDetailModel;
import com.gmartdev.komsi.g_mart.Model.ProductPesananDetailModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailPesananKeranjangActivity extends AppCompatActivity {

    List<ProductPesananDetailModel> mList = new ArrayList<>();

    String nama_kios, alamat_konsumen, id_order, token_konsumen, id_keranjang, id_konsumen, total_harga;

    TextView namaKios, alamatKonsumen, totalHarga;

    Spinner metodePengiriman;

    private RecyclerView recyclerView;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_keranjang);

//        mList = new ArrayList<>();
//        mList.add(new DetailPesananKeranjangModel("Beras", "25000", "2", "50000"));
//        mList.add(new DetailPesananKeranjangModel("Rokok Djarum 12", "15000", "1", "15000"));
//        mList.add(new DetailPesananKeranjangModel("Sabun Lifebouy", "2000", "3", "6000"));

        recyclerView = (RecyclerView) findViewById(R.id.listPesananKeranjang);

        namaKios = (TextView) findViewById(R.id.textStoreName);
        alamatKonsumen = (TextView) findViewById(R.id.alamatPengiriman);
        totalHarga = (TextView) findViewById(R.id.costTotalDetailPesananKeranjang);

        metodePengiriman = (Spinner) findViewById(R.id.spinnerMetodePengiriman);

        getData();
        setData();

        callApi();

    }

    private void getData(){
        if(getIntent().hasExtra("nama_kios") && getIntent().hasExtra("alamat_konsumen")){

            nama_kios = getIntent().getStringExtra("nama_kios");
            alamat_konsumen = getIntent().getStringExtra("alamat_konsumen");
            total_harga = getIntent().getStringExtra("total_harga");

        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        namaKios.setText(nama_kios);
        alamatKonsumen.setText(alamat_konsumen);
        totalHarga.setText(total_harga);
    }

    private void callApi(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_order = getIntent().getStringExtra("id_order");
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        id_keranjang = getIntent().getStringExtra("id_keranjang");
        Call<GetProduckPesananDetailModel> call = api.getProdukPesananDetailKeranjang(token_konsumen, id_konsumen, id_keranjang);
        call.enqueue(new Callback<GetProduckPesananDetailModel>() {
            @Override
            public void onResponse(Call<GetProduckPesananDetailModel> call, Response<GetProduckPesananDetailModel> response) {
                if(response.body().getResult() != null){
                    List<ProductPesananDetailModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    for (ProductPesananDetailModel productPesananDetailModel : list){
                        mList.add(new ProductPesananDetailModel( productPesananDetailModel.getNama_produk(), productPesananDetailModel.getMerk(), productPesananDetailModel.getHarga(), productPesananDetailModel.getJumlah_pesan(), productPesananDetailModel.getId_produkkios()));
                    }

                    DetailPesananKeranjangAdapter detailPesananKeranjangAdapter = new DetailPesananKeranjangAdapter(DetailPesananKeranjangActivity.this, mList);
                    recyclerView.setAdapter(detailPesananKeranjangAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DetailPesananKeranjangActivity.this));
                }
            }

            @Override
            public void onFailure(Call<GetProduckPesananDetailModel> call, Throwable t) {

            }
        });

    }
}
