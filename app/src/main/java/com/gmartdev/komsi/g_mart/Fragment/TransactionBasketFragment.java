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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Adapter.TransactionBasketAdapter;
import com.gmartdev.komsi.g_mart.Model.GetPesananModel;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.Model.ProductDetailPesananModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionBasketFragment extends Fragment {

    private static final String TAG = "TransactionBasket";

    List<PesananModel> mList = new ArrayList<>();
    Activity context;

    private List<ProductDetailPesananModel> mDataProduk ;

    private RecyclerView recyclerView;
    String id_konsumen, token_konsumen;

    ProgressBar progressBar;
    TextView keranjangIsEmpty;

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
        
        progressBar = (ProgressBar) v.findViewById(R.id.progressBarKeranjang);
        keranjangIsEmpty = (TextView) v.findViewById(R.id.teksKeranjangIsEmpty);
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
    }


    private void callApi() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Log.d(TAG, "callApi: ");
        Call<GetPesananModel> call = api.getPesananKeranjang(token_konsumen, id_konsumen);
        call.enqueue(new Callback<GetPesananModel>() {
            @Override
            public void onResponse(Call<GetPesananModel> call, Response<GetPesananModel> response) {
                Log.d(TAG, "onResponse: Kenapa ");
                if (response.body().getResult() != null) {
                    List<PesananModel> list = response.body().getResult();

                    Log.d(TAG, "Code :" + response.body().getResult());
                    for (PesananModel pesananModel : list) {
                        List<ProductDetailPesananModel> listProduk = pesananModel.getProduk();
                        mDataProduk = new ArrayList<>();
                        for(ProductDetailPesananModel productDetailPesananModel : listProduk){
                            mDataProduk.add(new ProductDetailPesananModel(productDetailPesananModel.getMerk(),
                                    productDetailPesananModel.getNama_produk()));
                        }
                        mList.add(new PesananModel(pesananModel.getId_order(), pesananModel.getSubtotal_harga(),
                                pesananModel.getStatus(), pesananModel.getMetode_kirim(),mDataProduk,
                                pesananModel.getNama_kios(), pesananModel.getAlamat_konsumen(), pesananModel.getTotal(),
                                pesananModel.getId_keranjang(), pesananModel.getCart(), pesananModel.getId_kios(),
                                pesananModel.getId_pembayaran(), pesananModel.getId_pengiriman()));
                    }
                    Log.d(TAG, "Data " + mList);
                    progressBar.setVisibility(View.GONE);
                    keranjangIsEmpty.setVisibility(View.GONE);
                    TransactionBasketAdapter transactionBasketAdapter = new TransactionBasketAdapter(getContext(), mList);
                    recyclerView.setAdapter(transactionBasketAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    progressBar.setVisibility(View.GONE);
                    keranjangIsEmpty.setVisibility(View.VISIBLE);
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
