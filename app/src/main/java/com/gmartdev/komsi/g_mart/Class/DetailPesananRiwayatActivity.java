package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gmartdev.komsi.g_mart.Adapter.DetailPesananRiwayatAdapter;
import com.gmartdev.komsi.g_mart.Model.DetailPesananRiwayatModel;
import com.gmartdev.komsi.g_mart.Model.ItemAtStoreModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

public class DetailPesananRiwayatActivity extends AppCompatActivity {

    List<DetailPesananRiwayatModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_riwayat);


        mList = new ArrayList<>();
        mList.add(new DetailPesananRiwayatModel("Beras", "25000", "2", "50000"));
        mList.add(new DetailPesananRiwayatModel("Rokok Djarum 12", "15000", "1", "15000"));
        mList.add(new DetailPesananRiwayatModel("Sabun Lifebouy", "2000", "3", "6000"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listPesananRiwayat);
        DetailPesananRiwayatAdapter detailPesananRiwayatAdapter = new DetailPesananRiwayatAdapter(DetailPesananRiwayatActivity.this, mList);
        recyclerView.setAdapter(detailPesananRiwayatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailPesananRiwayatActivity.this));
    }
}

