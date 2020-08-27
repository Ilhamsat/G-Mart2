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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

    List<String> spinnerItem = new ArrayList<String>();

    String storeName, storeDistance, id_kios, token_konsumen, id_konsumen, id_pengiriman, id_pembayaran, totalHarga, selected_item_spinner, lokasi_kios, latitude, longitude;

    int total_harga_update = 0;
    int total_harga_peritem = 0;

    MaterialButton btnAddBasket;
    TextView storeNameTitle;
    TextView storeAddress;
    TextView totalPesanan;
    TextView totalPriceText;
    TextView lihatPesanan;
    ImageButton buttonBack, mapStore;
    RelativeLayout popUpLihatKeranjang;
    Spinner metodePengiriman;

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
        metodePengiriman = (Spinner) findViewById(R.id.spinnerMetodePengiriman);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>  (this,R.layout.spinner_item_metode_pengiriman, spinnerItem);
        spinnerItem.add("Diantar");
        spinnerItem.add("Diambil");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metodePengiriman.setAdapter(dataAdapter);
        AdapterView.OnItemSelectedListener myListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                selected_item_spinner = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(0);
                selected_item_spinner = "Diantar";
            }
        };
        metodePengiriman.setOnItemSelectedListener(myListener);

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
                                total_harga_peritem = Integer.parseInt(listNilai.get(j).getJumlah_pesan()) * Integer.parseInt(listNilai.get(j).getHarga());
                                total_harga_update = total_harga_update + total_harga_peritem;
                            }
                        }
                    if(selected_item_spinner == "Diantar"){
                        id_pengiriman = "1";
                    }else if(selected_item_spinner == "Diambil"){
                        id_pengiriman = "2";
                    }
                        totalPriceText.setText(String.valueOf(total_harga_update));
                        total_harga_update = 0;
                        createPesanan();
                        totalPesanan.setText(String.valueOf(listNilaiFix.size()) + " " +"Pesanan");
                        popUpLihatKeranjang.setVisibility(View.VISIBLE);
                        listNilaiFix.clear();
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
                Log.d(TAG, "onClick: latitude " + latitude);
                Log.d(TAG, "onClick: longitude " + longitude);
                Intent intent = new Intent(StoreActivity.this, MapsActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);
            }
        });

        lihatPesanan = (TextView) findViewById(R.id.textLihatPesanan);
        lihatPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StoreActivity.this, "Silahkan ke bagian Transaksi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        storeNameTitle = (TextView) findViewById(R.id.store_name);
        storeAddress = (TextView) findViewById(R.id.textLocation);
        recyclerView = (RecyclerView) findViewById(R.id.list_item_at_store);
        callApi();

    }

    private void callApi(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_kios = getIntent().getStringExtra("id_kios");
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetProductModel> call = api.getProdukKios(id_kios, token_konsumen);
        call.enqueue(new Callback<GetProductModel>() {
            @Override
            public void onResponse(Call<GetProductModel> call, Response<GetProductModel> response) {

                if(response.body().getResult() != null){
                    lokasi_kios = response.body().getLokasi_kios();
                    String[] parts = lokasi_kios.split(",");
                    latitude = parts[0];
                    longitude = parts[1];

                    List<ProductModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    storeNameTitle.setText(list.get(0).getNama_kios());
                    storeAddress.setText(list.get(0).getAlamat_kios());
                    for (ProductModel productModel : list){
                        mList.add(new ProductModel(productModel.getId_produkkios(), productModel.getMerk(),
                                productModel.getNama_produk(), productModel.getHarga(), productModel.getGambar()));
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
                    storeNameTitle.setText(getIntent().getStringExtra("nama_kios"));
                    storeAddress.setText(getIntent().getStringExtra("alamat_kios"));
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

        Call<GetCartModel> call = api.setNewKeranjang(id_pembayaran, id_pengiriman, id_kios, id_konsumen, jsonArray,
                token_konsumen);
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
