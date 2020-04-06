package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gmartdev.komsi.g_mart.Adapter.ItemStoreCategoryAdapter;
import com.gmartdev.komsi.g_mart.Model.ItemStoreCategoryModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

public class BasicNeedsCategoryActivity extends AppCompatActivity {

    List<ItemStoreCategoryModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_needs_category);

        mList = new ArrayList<>();
        mList.add(new ItemStoreCategoryModel("Beras", "25000", "Toko Shamila", "1,3"));
        mList.add(new ItemStoreCategoryModel("Garam", "2000", "Toko Shamila", "1,3"));
        mList.add(new ItemStoreCategoryModel("Gula", "3000", "Toko Shamila", "1,3"));
        mList.add(new ItemStoreCategoryModel("Telur", "25000", "Toko Shamila", "1,3"));
        mList.add(new ItemStoreCategoryModel("Beras", "24000", "Toko Lia", "1,4"));
        mList.add(new ItemStoreCategoryModel("Garam", "3000", "Toko Lia", "1,3"));
        mList.add(new ItemStoreCategoryModel("Gula", "4000", "Toko Lia", "1,3"));
        mList.add(new ItemStoreCategoryModel("Telur", "26000", "Toko Lia", "1,3"));
        mList.add(new ItemStoreCategoryModel("Beras", "20000", "Toko Udin", "2"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listItemBasicNeeds);
        ItemStoreCategoryAdapter itemStoreCategoryAdapter = new ItemStoreCategoryAdapter(BasicNeedsCategoryActivity.this, mList);
        recyclerView.setAdapter(itemStoreCategoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BasicNeedsCategoryActivity.this));




    }
}
