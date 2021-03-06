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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.ItemStoreCategoryAdapter;
import com.gmartdev.komsi.g_mart.Model.GetProductCategoryModel;
import com.gmartdev.komsi.g_mart.Model.ProductCategoryModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CleaningToolsCategoryActivity extends AppCompatActivity {

    List<ProductCategoryModel> mList = new ArrayList<>();
    ItemStoreCategoryAdapter itemStoreCategoryAdapter;

    private RecyclerView recyclerView;
    String id_konsumen, token_konsumen;

    TextInputLayout searchCleaningTools;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_tools_category);

        searchCleaningTools = (TextInputLayout) findViewById(R.id.searchCleaningTools);
        searchCleaningTools.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                itemStoreCategoryAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.listItemCleaningTools);

        callApi();
    }

    private void callApi(){
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetProductCategoryModel> call = api.getProductCategory(token_konsumen, "9");
        call.enqueue(new Callback<GetProductCategoryModel>() {
            @Override
            public void onResponse(Call<GetProductCategoryModel> call, Response<GetProductCategoryModel> response) {


                if (response.body().getResult() != null){
                    List<ProductCategoryModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    for (ProductCategoryModel productCategoryModel : list){
                        mList.add(new ProductCategoryModel( productCategoryModel.getNama_produk(), productCategoryModel.getMerk(), productCategoryModel.getHarga(), productCategoryModel.getNama_kios(), productCategoryModel.getId_kios(), productCategoryModel.getRating(), productCategoryModel.getGambar(), productCategoryModel.getLatitude(), productCategoryModel.getLongitude()));
                    }
                    Log.d(TAG, "Data " + mList);

                    itemStoreCategoryAdapter = new ItemStoreCategoryAdapter(CleaningToolsCategoryActivity.this, mList);
                    recyclerView.setAdapter(itemStoreCategoryAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CleaningToolsCategoryActivity.this));

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
