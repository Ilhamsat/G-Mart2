package com.gmartdev.komsi.g_mart.Fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.TransactionHistoryAdapter;
import com.gmartdev.komsi.g_mart.Model.GetPesananModel;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.Model.ProductDetailPesananModel;
import com.gmartdev.komsi.g_mart.Model.TransactionHistoryModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionHistoryFragment extends Fragment {

    List<PesananModel> mList = new ArrayList<>();
    Activity context;
    private List<ProductDetailPesananModel> mDataProduk ;

    int images[] = {R.drawable.ic_success_white,R.drawable.ic_not_success_white};

    private RecyclerView recyclerView;
    String id_konsumen, token_konsumen;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_transaction_history, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.history);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callApi();

//        mList = new ArrayList<>();
//        mList.add(new TransactionHistoryModel("Indomie, dan 2 barang lainnya","13000", "Terkirim", images[0]));
//        mList.add(new TransactionHistoryModel("Rokok Gudang Garam","20000", "Dibatalkan", images[1]));
//        mList.add(new TransactionHistoryModel("Teh Kotak","5000", "Dibatalkan", images[1]));
//        mList.add(new TransactionHistoryModel("Galon Aqua","110000", "Terkirim", images[0]));
    }


    private void callApi(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetPesananModel> call = api.getPesananRiwayat(token_konsumen, id_konsumen);
        call.enqueue(new Callback<GetPesananModel>() {
            @Override
            public void onResponse(Call<GetPesananModel> call, Response<GetPesananModel> response) {


                if (response.body().getResult() != null){
                    List<PesananModel> list = response.body().getResult();
                    Log.d(TAG, "Code :" + response.body().getResult());
                    for (PesananModel pesananModel : list){
                        List<ProductDetailPesananModel> listProduk = pesananModel.getProduk();
                        mDataProduk = new ArrayList<>();
                        for(ProductDetailPesananModel productDetailPesananModel : listProduk){
                            mDataProduk.add(new ProductDetailPesananModel(productDetailPesananModel.getMerk(),productDetailPesananModel.getNama_produk()));
                        }
                        mList.add(new PesananModel(pesananModel.getId_order(), pesananModel.getSubtotal_harga(), pesananModel.getStatus(), pesananModel.getMetode_kirim(),mDataProduk, pesananModel.getNama_kios(), pesananModel.getAlamat_konsumen()));

                    }
                    Log.d(TAG, "Data " + mList);

                    TransactionHistoryAdapter transactionHistoryAdapter = new TransactionHistoryAdapter(getContext(),mList);
                    recyclerView.setAdapter(transactionHistoryAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    Log.d(TAG, "Code :" + response.body().getMessage());
                    Log.d(TAG, "Code :" + id_konsumen);
                    Log.d(TAG, "Code :" + token_konsumen);
                    return;
                }
            }

            @Override
            public void onFailure(Call<GetPesananModel> call, Throwable t) {

            }
        });
    }
}
