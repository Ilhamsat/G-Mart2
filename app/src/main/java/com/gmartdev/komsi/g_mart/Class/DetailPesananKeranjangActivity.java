package com.gmartdev.komsi.g_mart.Class;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.gmartdev.komsi.g_mart.Adapter.DetailPesananKeranjangAdapter;
import com.gmartdev.komsi.g_mart.Model.DetailPesananKeranjangModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

public class DetailPesananKeranjangActivity extends AppCompatActivity {

    List<DetailPesananKeranjangModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_keranjang);

        mList = new ArrayList<>();
        mList.add(new DetailPesananKeranjangModel("Beras", "25000", "2", "50000"));
        mList.add(new DetailPesananKeranjangModel("Rokok Djarum 12", "15000", "1", "15000"));
        mList.add(new DetailPesananKeranjangModel("Sabun Lifebouy", "2000", "3", "6000"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listPesananKeranjang);
        DetailPesananKeranjangAdapter detailPesananKeranjangAdapter = new DetailPesananKeranjangAdapter(DetailPesananKeranjangActivity.this, mList);
        recyclerView.setAdapter(detailPesananKeranjangAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailPesananKeranjangActivity.this));

    }
}
