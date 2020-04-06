package com.gmartdev.komsi.g_mart.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gmartdev.komsi.g_mart.Adapter.StoreRecommendationAdapter;
import com.gmartdev.komsi.g_mart.Class.BasicNeedsCategoryActivity;
import com.gmartdev.komsi.g_mart.Class.StoreActivity;
import com.gmartdev.komsi.g_mart.Model.StoreRecomendationModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    List<StoreRecomendationModel> mList;
    Activity context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.store_recommendation);
        StoreRecommendationAdapter storeRecommendationAdapter = new StoreRecommendationAdapter(getContext(),mList);
        recyclerView.setAdapter(storeRecommendationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mList.add(new StoreRecomendationModel("Toko Pelangi","1,8"));
        mList.add(new StoreRecomendationModel("Toko Shamila","1,3"));
        mList.add(new StoreRecomendationModel("Toko Swalayan Laura","2"));
        mList.add(new StoreRecomendationModel("Toko Cina","1"));
        mList.add(new StoreRecomendationModel("Toko Ulin","2"));
        mList.add(new StoreRecomendationModel("Toko Mama Lia","1,4"));
    }

    @Override
    public void onStart() {
        super.onStart();

        RelativeLayout sembako = (RelativeLayout) context.findViewById(R.id.sembako);
        sembako.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BasicNeedsCategoryActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout makanan = (RelativeLayout) context.findViewById(R.id.makanan);
        makanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
