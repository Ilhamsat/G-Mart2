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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.ItemAtStoreAdapter;
import com.gmartdev.komsi.g_mart.Model.CartModel;
import com.gmartdev.komsi.g_mart.Model.GetCartModel;
import com.gmartdev.komsi.g_mart.Model.GetPesananModel;
import com.gmartdev.komsi.g_mart.Model.GetProductModel;
import com.gmartdev.komsi.g_mart.Model.ItemAtStoreModel;
import com.gmartdev.komsi.g_mart.Model.ProductModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StoreActivity extends AppCompatActivity implements  HelperKeranjang{



    List<ProductModel> mList = new ArrayList<>();
    List<CartModel> mCart = new ArrayList<>();

    String storeName, storeDistance, id_kios, token_konsumen, id_konsumen, id_pengiriman, id_pembayaran;

    MaterialButton btnAddBasket;
    TextView storeNameTitle;
    TextView storeAddress;
    ImageButton buttonBack;

    private RecyclerView recyclerView;
    ItemAtStoreAdapter itemAtStoreAdapter;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    HelperKeranjang helperKeranjang = new HelperKeranjang() {
        @Override
        public void setValues(List<Nilai> arrayList) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        buttonBack = (ImageButton) findViewById(R.id.backInStore);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddBasket = findViewById(R.id.insertBasket);
        btnAddBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        mList = new ArrayList<>();
//        mList.add(new ItemAtStoreModel("Beras", "25000"));
//        mList.add(new ItemAtStoreModel("Gula", "5000"));
//        mList.add(new ItemAtStoreModel("Garam", "3000"));
//        mList.add(new ItemAtStoreModel("Minyak", "5000"));
        storeNameTitle = (TextView) findViewById(R.id.store_name);
        storeAddress = (TextView) findViewById(R.id.textLocation);
        recyclerView = (RecyclerView) findViewById(R.id.list_item_at_store);
        callApi();


        //getData();
        //setData();
    }

//    private void getData(){
//        if(getIntent().hasExtra("storeName") && getIntent().hasExtra("storeDistance")){
//
//            storeName = getIntent().getStringExtra("storeName");
//            storeDistance = getIntent().getStringExtra("storeDistance");
//
//        }else {
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void setData(){
//        storeNameTitle.setText(storeName);
//    }

    private void callApi(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_kios = getIntent().getStringExtra("id_kios");
        token_konsumen = sharedPreferences.getString("token", null);

        Call<GetProductModel> call = api.getProdukKios(id_kios, token_konsumen);
        call.enqueue(new Callback<GetProductModel>() {
            @Override
            public void onResponse(Call<GetProductModel> call, Response<GetProductModel> response) {



                if(response.body().getResult() != null){
                    List<ProductModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    storeNameTitle.setText(list.get(0).getNama_kios());
                    storeAddress.setText(list.get(0).getAlamat_kios());
                    for (ProductModel productModel : list){
                        mList.add(new ProductModel(productModel.getMerk(), productModel.getNama_produk(), productModel.getHarga(), productModel.getGambar()));
                    }

                    itemAtStoreAdapter = new ItemAtStoreAdapter(StoreActivity.this, mList);
                    recyclerView.setAdapter(itemAtStoreAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(StoreActivity.this));
                }else {
                    Log.d(TAG, "Code :" + id_kios);
                    Log.d(TAG, "Code :" + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetProductModel> call, Throwable t) {

            }
        });
    }


    private void createPesanan(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_kios = getIntent().getStringExtra("id_kios");
        token_konsumen = sharedPreferences.getString("token", null);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        id_pembayaran = "1";
        id_pengiriman = "1";
        List<CartModel> cart;
        HashMap<String, String> t = new HashMap<>();


        Call<GetCartModel> call = api.setNewKeranjang(id_pembayaran, id_pengiriman, id_kios, id_konsumen, t, token_konsumen);
    }


    @Override
    public void setValues(List<Nilai> arrayList) {

    }

//    @Override
//    public List<Nilai> HelperKeranjang(String id_produkkios, String jumlah, String harga) {
//        List<Nilai> nilaiKereanjang = new ArrayList<>();
//        return nilaiKereanjang;
//    }

}
