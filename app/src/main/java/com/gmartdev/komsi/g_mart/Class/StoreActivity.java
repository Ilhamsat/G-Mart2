package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.Adapter.ItemAtStoreAdapter;
import com.gmartdev.komsi.g_mart.Model.ItemAtStoreModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    List<ItemAtStoreModel> mList;

    String storeName;
    String storeDistance;

    TextView storeNameTitle;
    ImageButton buttonBack;

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


        mList = new ArrayList<>();
        mList.add(new ItemAtStoreModel("Beras", "25000"));
        mList.add(new ItemAtStoreModel("Gula", "5000"));
        mList.add(new ItemAtStoreModel("Garam", "3000"));
        mList.add(new ItemAtStoreModel("Minyak", "5000"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_item_at_store);
        ItemAtStoreAdapter itemAtStoreAdapter = new ItemAtStoreAdapter(StoreActivity.this, mList);
        recyclerView.setAdapter(itemAtStoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StoreActivity.this));

        storeNameTitle = (TextView) findViewById(R.id.store_name);

        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("storeName") && getIntent().hasExtra("storeDistance")){

            storeName = getIntent().getStringExtra("storeName");
            storeDistance = getIntent().getStringExtra("storeDistance");

        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        storeNameTitle.setText(storeName);
    }

    private void callApi(){

    }
}
