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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.DetailPesananKeranjangAdapter;
import com.gmartdev.komsi.g_mart.Fragment.HomeFragment;
import com.gmartdev.komsi.g_mart.Fragment.TransactionBasketFragment;
import com.gmartdev.komsi.g_mart.Model.DetailPesananKeranjangModel;
import com.gmartdev.komsi.g_mart.Model.GetCartModel;
import com.gmartdev.komsi.g_mart.Model.GetProduckPesananDetailModel;
import com.gmartdev.komsi.g_mart.Model.ProductPesananDetailModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailPesananKeranjangActivity extends AppCompatActivity {

    private static final String TAG = "DetailKeranjang";

    List<ProductPesananDetailModel> mList = new ArrayList<>();
    List<Nilai> listNilai = new ArrayList<>();
    List<Nilai> listNilaiFix = new ArrayList<>();

    List<String> spinnerItem = new ArrayList<String>();

    String nama_kios, alamat_konsumen, id_order, token_konsumen, id_keranjang, id_konsumen, id_pengiriman, total_harga, selected_item_spinner;

    int total_harga_update = 0;
    int total_harga_peritem = 0;

    TextView namaKios, alamatKonsumen, totalHarga;
    Spinner metodePengiriman;
    MaterialButton simpanDetailPesananKeranjang;

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

        simpanDetailPesananKeranjang = (MaterialButton) findViewById(R.id.simpanDetailPesananKeranjang);

        id_keranjang = getIntent().getStringExtra("id_keranjang");

        simpanDetailPesananKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listNilai != null){
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

                    if (listNilaiFix.isEmpty()){
                        Log.d(TAG, "onClick: ListNilaiFix NULL");
                        deleteKeranjang(id_keranjang);
                    }else {
                        Log.d(TAG, "onClick: total harga " + total_harga_update);
                        Log.d(TAG, "onClick: Selected item spinner " + selected_item_spinner);
                        Log.d(TAG, "onClick: Selected item spinner " + id_pengiriman);
                        updateKeranjang(id_keranjang, id_pengiriman, String.valueOf(total_harga_update));

                    }
                }else {
                    Log.d(TAG, "onClick: ListNilai NULL");
                }
            }
        });

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
        totalHarga.setText(String.valueOf(total_harga));
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

                    HelperKeranjang helperKeranjang = new HelperKeranjang() {
                        @Override
                        public void setValues(List<Nilai> arrayList) {
                            listNilai = arrayList;
                        }
                    };

                    DetailPesananKeranjangAdapter detailPesananKeranjangAdapter = new DetailPesananKeranjangAdapter(DetailPesananKeranjangActivity.this, mList, helperKeranjang);
                    recyclerView.setAdapter(detailPesananKeranjangAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(DetailPesananKeranjangActivity.this));
                }
            }

            @Override
            public void onFailure(Call<GetProduckPesananDetailModel> call, Throwable t) {

            }
        });

    }

    private void deleteKeranjang(String id_keranjang){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Log.d(TAG, "deleteKeranjang: id keranjang " + id_keranjang);
        Call<GetCartModel> call = api.deleteKeranjang(token_konsumen, id_konsumen, id_keranjang);
        call.enqueue(new Callback<GetCartModel>() {
            @Override
            public void onResponse(Call<GetCartModel> call, Response<GetCartModel> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang +" berhasil di batalkan");
                    Toast.makeText(DetailPesananKeranjangActivity.this, "Keranjang ini telah dihapus", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailPesananKeranjangActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang +" gagal di batalkan");
                }
            }

            @Override
            public void onFailure(Call<GetCartModel> call, Throwable t) {

            }
        });
    }

    private void updateKeranjang(String id_keranjang, String id_pengiriman, String total_harga){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
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
//        Log.d(TAG, "onClick: " + jsonArray);
        Call<GetCartModel> call = api.updateKeranjang(token_konsumen, id_konsumen, id_keranjang, jsonArray, id_pengiriman, total_harga);
        call.enqueue(new Callback<GetCartModel>() {
            @Override
            public void onResponse(Call<GetCartModel> call, Response<GetCartModel> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang +" berhasil di update");
                    Toast.makeText(DetailPesananKeranjangActivity.this, "Keranjang ini berhasil di update", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailPesananKeranjangActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang +" gagal di update");
                }
            }

            @Override
            public void onFailure(Call<GetCartModel> call, Throwable t) {

            }
        });

    }

}
