package com.gmartdev.komsi.g_mart.Fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmartdev.komsi.g_mart.Adapter.TransactionBasketAdapter;
import com.gmartdev.komsi.g_mart.Model.TransactionBasketModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionBasketFragment extends Fragment {

    List<TransactionBasketModel> mList;
    Activity context;

    public TransactionBasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_transaction_basket, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.basket);
        TransactionBasketAdapter transactionBasketAdapter = new TransactionBasketAdapter(getContext(),mList);
        recyclerView.setAdapter(transactionBasketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = new ArrayList<>();
        mList.add(new TransactionBasketModel("2+" ,"Indomie, dan 2 barang lainnya","13000", "Diambil"));
        mList.add(new TransactionBasketModel("3+" ,"Gula, dan 3 barang lainnya","20000", "Diantar"));
        mList.add(new TransactionBasketModel("1" ,"Beras 5Kg","50000", "Diambil"));
        mList.add(new TransactionBasketModel("4+" ,"Minyak, dan 4 barang lainnya","13000", "Diantar"));
    }
}
