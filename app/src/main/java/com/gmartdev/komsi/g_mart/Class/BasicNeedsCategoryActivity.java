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

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.ItemStoreCategoryAdapter;
import com.gmartdev.komsi.g_mart.Model.GetProductCategoryModel;
import com.gmartdev.komsi.g_mart.Model.ItemStoreCategoryModel;
import com.gmartdev.komsi.g_mart.Model.ProductCategoryModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BasicNeedsCategoryActivity extends AppCompatActivity {

    List<ProductCategoryModel> mList = new ArrayList<>();

    private RecyclerView recyclerView;
    String id_konsumen, token_konsumen;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_needs_category);

//        mList = new ArrayList<>();
//        mList.add(new ItemStoreCategoryModel("Beras", "25000", "Toko Shamila", "1,3"));
//        mList.add(new ItemStoreCategoryModel("Garam", "2000", "Toko Shamila", "1,3"));
//        mList.add(new ItemStoreCategoryModel("Gula", "3000", "Toko Shamila", "1,3"));
//        mList.add(new ItemStoreCategoryModel("Telur", "25000", "Toko Shamila", "1,3"));
//        mList.add(new ItemStoreCategoryModel("Beras", "24000", "Toko Lia", "1,4"));
//        mList.add(new ItemStoreCategoryModel("Garam", "3000", "Toko Lia", "1,3"));
//        mList.add(new ItemStoreCategoryModel("Gula", "4000", "Toko Lia", "1,3"));
//        mList.add(new ItemStoreCategoryModel("Telur", "26000", "Toko Lia", "1,3"));
//        mList.add(new ItemStoreCategoryModel("Beras", "20000", "Toko Udin", "2"));

        recyclerView = (RecyclerView) findViewById(R.id.listItemBasicNeeds);

        callApi();

    }

    private void callApi(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetProductCategoryModel> call = api.getProductCategory(token_konsumen, "1");
        call.enqueue(new Callback<GetProductCategoryModel>() {
            @Override
            public void onResponse(Call<GetProductCategoryModel> call, Response<GetProductCategoryModel> response) {


                if (response.body().getResult() != null){
                    List<ProductCategoryModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    for (ProductCategoryModel productCategoryModel : list){
                        mList.add(new ProductCategoryModel( productCategoryModel.getNama_produk(), productCategoryModel.getMerk(), productCategoryModel.getHarga(), productCategoryModel.getNama_kios()));
                    }
                    Log.d(TAG, "Data " + mList);

                    ItemStoreCategoryAdapter itemStoreCategoryAdapter = new ItemStoreCategoryAdapter(BasicNeedsCategoryActivity.this, mList);
                    recyclerView.setAdapter(itemStoreCategoryAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(BasicNeedsCategoryActivity.this));

                } else {
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    Log.d(TAG, "Code :" + id_konsumen);
                    Log.d(TAG, "Code :" + token_konsumen);
                    return;
                }
            }

            @Override
            public void onFailure(Call<GetProductCategoryModel> call, Throwable t) {

            }
        });
    }
}
