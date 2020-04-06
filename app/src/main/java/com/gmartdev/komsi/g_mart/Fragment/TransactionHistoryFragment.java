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

import com.gmartdev.komsi.g_mart.Adapter.TransactionHistoryAdapter;
import com.gmartdev.komsi.g_mart.Model.TransactionHistoryModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionHistoryFragment extends Fragment {

    List<TransactionHistoryModel> mList;
    Activity context;

    int images[] = {R.drawable.ic_success_white,R.drawable.ic_not_success_white};

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_transaction_history, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.history);
        TransactionHistoryAdapter transactionHistoryAdapter = new TransactionHistoryAdapter(getContext(),mList);
        recyclerView.setAdapter(transactionHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mList.add(new TransactionHistoryModel("Indomie, dan 2 barang lainnya","13000", "Terkirim", images[0]));
        mList.add(new TransactionHistoryModel("Rokok Gudang Garam","20000", "Dibatalkan", images[1]));
        mList.add(new TransactionHistoryModel("Teh Kotak","5000", "Dibatalkan", images[1]));
        mList.add(new TransactionHistoryModel("Galon Aqua","110000", "Terkirim", images[0]));
    }
}
