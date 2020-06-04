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
import com.gmartdev.komsi.g_mart.Model.ItemStoreCategoryModel;
import com.gmartdev.komsi.g_mart.Model.ProductCategoryModel;
import com.gmartdev.komsi.g_mart.Model.ProductPesananDetailModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BasicNeedsCategoryActivity extends AppCompatActivity {

    List<ProductCategoryModel> mList = new ArrayList<>();
    ItemStoreCategoryAdapter itemStoreCategoryAdapter;

    private RecyclerView recyclerView;
    String id_konsumen, token_konsumen;

    TextInputLayout searchBasicNeeds;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_needs_category);

        searchBasicNeeds = (TextInputLayout) findViewById(R.id.searchBasicNeeds);
        searchBasicNeeds.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                itemStoreCategoryAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                filter(editable.toString());
            }
        });

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

//    private void filter(String text){
//        List<ProductCategoryModel> filteredList = new ArrayList<>();
//
//        for (ProductCategoryModel item : mList){
//            if (item.getMerk().toLowerCase().contains(text.toLowerCase())){
//                filteredList.add(item);
//            }
//        }
//    }

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
                        mList.add(new ProductCategoryModel( productCategoryModel.getNama_produk(), productCategoryModel.getMerk(), productCategoryModel.getHarga(), productCategoryModel.getNama_kios(), productCategoryModel.getId_kios()));
                    }
                    Log.d(TAG, "Data " + mList);

                    itemStoreCategoryAdapter = new ItemStoreCategoryAdapter(BasicNeedsCategoryActivity.this, mList);
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
