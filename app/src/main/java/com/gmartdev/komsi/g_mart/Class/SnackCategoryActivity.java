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
import com.gmartdev.komsi.g_mart.Model.ProductCategoryModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SnackCategoryActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_snack_category);

        recyclerView = (RecyclerView) findViewById(R.id.listItemSnack);

        callApi();
    }

    private void callApi(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetProductCategoryModel> call = api.getProductCategory(token_konsumen, "2");
        call.enqueue(new Callback<GetProductCategoryModel>() {
            @Override
            public void onResponse(Call<GetProductCategoryModel> call, Response<GetProductCategoryModel> response) {


                if (response.body().getResult() != null){
                    List<ProductCategoryModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    for (ProductCategoryModel productCategoryModel : list){
                        mList.add(new ProductCategoryModel( productCategoryModel.getNama_produk(), productCategoryModel.getMerk(), productCategoryModel.getHarga(), productCategoryModel.getNama_kios(), productCategoryModel.getId_kios()));
                    }
                    Log.d(TAG, "Data " + mList);

                    ItemStoreCategoryAdapter itemStoreCategoryAdapter = new ItemStoreCategoryAdapter(SnackCategoryActivity.this, mList);
                    recyclerView.setAdapter(itemStoreCategoryAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SnackCategoryActivity.this));

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
