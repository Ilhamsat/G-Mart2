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
import com.gmartdev.komsi.g_mart.Adapter.TransactionBasketAdapter;
import com.gmartdev.komsi.g_mart.Model.GetPesananModel;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.Model.ProductDetailPesananModel;
import com.gmartdev.komsi.g_mart.Model.TransactionBasketModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionBasketFragment extends Fragment {

    List<PesananModel> mList = new ArrayList<>();
    Activity context;

    private List<ProductDetailPesananModel> mDataProduk ;

    private RecyclerView recyclerView;
    String id_konsumen, token_konsumen;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    public TransactionBasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getActivity();

        View v = inflater.inflate(R.layout.fragment_transaction_basket, container, false);

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
//        id_konsumen = sharedPreferences.getString("id_konsumen", null);
//        token_konsumen = sharedPreferences.getString("token", null);

        recyclerView = (RecyclerView) v.findViewById(R.id.basket);
//        TransactionBasketAdapter transactionBasketAdapter = new TransactionBasketAdapter(getContext(),mList);
//        recyclerView.setAdapter(transactionBasketAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callApi();

//        mList = new ArrayList<>();
//        mList.add(new TransactionBasketModel("2+" ,"Indomie, dan 2 barang lainnya","13000", "Diambil"));
//        mList.add(new TransactionBasketModel("3+" ,"Gula, dan 3 barang lainnya","20000", "Diantar"));
//        mList.add(new TransactionBasketModel("1" ,"Beras 5Kg","50000", "Diambil"));
//        mList.add(new TransactionBasketModel("4+" ,"Minyak, dan 4 barang lainnya","13000", "Diantar"));
    }

    private void callApi() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetPesananModel> call = api.getPesananKeranjang(token_konsumen, id_konsumen);
        call.enqueue(new Callback<GetPesananModel>() {
            @Override
            public void onResponse(Call<GetPesananModel> call, Response<GetPesananModel> response) {


                if (response.body().getResult() != null) {
                    List<PesananModel> list = response.body().getResult();


                    Log.d(TAG, "Code :" + response.body().getResult());
                    for (PesananModel pesananModel : list) {
                        List<ProductDetailPesananModel> listProduk = pesananModel.getProduk();
                        mDataProduk = new ArrayList<>();
                        for(ProductDetailPesananModel productDetailPesananModel : listProduk){
                            mDataProduk.add(new ProductDetailPesananModel(productDetailPesananModel.getMerk(),productDetailPesananModel.getNama_produk()));
                        }
                        mList.add(new PesananModel(pesananModel.getId_order(), pesananModel.getSubtotal_harga(), pesananModel.getStatus(), pesananModel.getMetode_kirim(),mDataProduk, pesananModel.getNama_kios(), pesananModel.getAlamat_konsumen(), pesananModel.getTotal()));
                    }
                    Log.d(TAG, "Data " + mList);

                    TransactionBasketAdapter transactionBasketAdapter = new TransactionBasketAdapter(getContext(), mList);
                    recyclerView.setAdapter(transactionBasketAdapter);
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
