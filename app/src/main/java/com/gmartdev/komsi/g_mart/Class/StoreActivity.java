package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.ItemAtStoreAdapter;
import com.gmartdev.komsi.g_mart.Fragment.TransactionBasketFragment;
import com.gmartdev.komsi.g_mart.Model.GetCartModel;
import com.gmartdev.komsi.g_mart.Model.GetProductModel;
import com.gmartdev.komsi.g_mart.Model.ProductModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreActivity extends AppCompatActivity{

    private static final String TAG = "StoreActivity";

    List<Nilai> listNilaiFix = new ArrayList<>();
    List<Nilai> listNilai = new ArrayList<>();
    List<ProductModel> mList = new ArrayList<>();

    String storeName, storeDistance, id_kios, token_konsumen, id_konsumen, id_pengiriman, id_pembayaran, totalHarga, listPesananString;

    MaterialButton btnAddBasket;
    TextView storeNameTitle;
    TextView storeAddress;
    TextView totalPesanan;
    TextView totalPriceText;
    ImageButton buttonBack, mapStore;
    RelativeLayout popUpLihatKeranjang;

    TransactionBasketFragment transactionBasketFragment = new TransactionBasketFragment();

    private RecyclerView recyclerView;
    ItemAtStoreAdapter itemAtStoreAdapter;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        totalPriceText = (TextView) findViewById(R.id.totalPriceText);
        totalPesanan = (TextView) findViewById(R.id.totalItemBuy);
        popUpLihatKeranjang = (RelativeLayout) findViewById(R.id.lihatKeranjang);

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
                if(listNilai != null && listNilai.size() != 0){
                        for (int j = 0; j < listNilai.size(); j++){
                            if(Integer.parseInt(listNilai.get(j).getJumlah_pesan()) != 0){
                                listNilaiFix.add(new Nilai(listNilai.get(j).getId_produkkios(), listNilai.get(j).getJumlah_pesan(), listNilai.get(j).getHarga()));
                            }
                    }
                        createPesanan();
                        totalPesanan.setText(String.valueOf(listNilaiFix.size()) + " " +"Pesanan");
                        popUpLihatKeranjang.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "onClick: " + listNilai.size());
                    Toast.makeText(StoreActivity.this, "Kamu belum memilih apapun", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapStore = findViewById(R.id.mapStore);
        mapStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreActivity.this, MapsActivity.class);
                startActivity(intent);
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
                        mList.add(new ProductModel(productModel.getId_produkkios(), productModel.getMerk(), productModel.getNama_produk(), productModel.getHarga(), productModel.getGambar()));
                    }

                    HelperKeranjang helperKeranjang = new HelperKeranjang() {
                        @Override
                        public void setValues(List<Nilai> arrayList) {
                            listNilai = arrayList;
                        }
                    };

                    itemAtStoreAdapter = new ItemAtStoreAdapter(StoreActivity.this, mList, helperKeranjang);
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

        JSONArray jsonArray = new JSONArray();
            try {
                for (Nilai cart : listNilaiFix) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id_produkkios", cart.getId_produkkios());
                    jsonObject.put("jumlah_pesan", cart.getJumlah_pesan());
                    jsonObject.put("harga", cart.getHarga());
                    jsonArray.put(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        Call<GetCartModel> call = api.setNewKeranjang(id_pembayaran, id_pengiriman, id_kios, id_konsumen, jsonArray, token_konsumen);
        call.enqueue(new Callback<GetCartModel>() {
            @Override
            public void onResponse(Call<GetCartModel> call, Response<GetCartModel> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: berhasil tambah keranjang");
                    Log.d(TAG, "onResponse: " + jsonArray);
                } else {

                }
                Log.d(TAG, "onResponse: Gk masuk isSucces");
                Log.d(TAG, "onResponse: " + jsonArray);

                if(response.body()!=null){
                    Log.d(TAG, "onResponse: ada code message");
                }else{
                    Log.d(TAG, "onResponse:gak ada code message");

                }
                Log.d(TAG, "onResponse: " + response.body().getCode());

            }

            @Override
            public void onFailure(Call<GetCartModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        listNilaiFix.clear();
        finish();
    }
}
