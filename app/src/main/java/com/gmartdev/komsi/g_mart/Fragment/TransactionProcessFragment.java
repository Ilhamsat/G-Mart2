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
import com.gmartdev.komsi.g_mart.Adapter.TransactionProcessAdapter;
import com.gmartdev.komsi.g_mart.Adapter.TransactionProcessMenungguAdapter;
import com.gmartdev.komsi.g_mart.Model.GetPesananModel;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.Model.ProductDetailPesananModel;
import com.gmartdev.komsi.g_mart.Model.TransactionProcessModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionProcessFragment extends Fragment {

    List<PesananModel> mList = new ArrayList<>();
    List<PesananModel> mListMenunggu = new ArrayList<>();
    Activity context;
    private List<ProductDetailPesananModel> mDataProduk ;

    TextView menungguIsEmpty, diprosesIsEmpty;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewMenunggu;
    private RecyclerView recyclerViewDiantar;

    String id_konsumen, token_konsumen;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);


    public TransactionProcessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context=getActivity();

        View v = inflater.inflate(R.layout.fragment_transaction_process, container, false);

        menungguIsEmpty = (TextView) v.findViewById(R.id.teksMenungguIsEmpty);
        diprosesIsEmpty = (TextView) v.findViewById(R.id.teksProsesIsEmpty);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBarProcess);
        recyclerView = (RecyclerView) v.findViewById(R.id.process);
        recyclerViewMenunggu = (RecyclerView) v.findViewById(R.id.menunggu);
//        recyclerViewDiantar = (RecyclerView) v.findViewById(R.id.diantar);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callApi();
        callApiMenunggu();

//        mList = new ArrayList<>();
//        mList.add(new TransactionProcessModel("2+" ,"Indomie, dan 2 barang lainnya","13000", "Dikemas"));
//        mList.add(new TransactionProcessModel("3+" ,"Gula, dan 3 barang lainnya","20000", "Dikemas"));
//        mList.add(new TransactionProcessModel("1" ,"Beras 5Kg","50000", "Dikemas"));
//        mList.add(new TransactionProcessModel("4+" ,"Minyak, dan 4 barang lainnya","13000", "Dikemas"));
    }

    private void callApi(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetPesananModel> call = api.getPesananDiproses(id_konsumen, token_konsumen);
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
                        mList.add(new PesananModel(pesananModel.getId_order(), pesananModel.getSubtotal_harga(), pesananModel.getStatus(), pesananModel.getMetode_kirim(),mDataProduk, pesananModel.getNama_kios(), pesananModel.getAlamat_konsumen(), pesananModel.getNo_hp(), pesananModel.getBiaya_kirim()));
                    }
                    Log.d(TAG, "Data " + mList);

                    progressBar.setVisibility(View.GONE);
                    diprosesIsEmpty.setVisibility(View.GONE);
                    menungguIsEmpty.setVisibility(View.GONE);
                    TransactionProcessAdapter transactionProcessAdapter = new TransactionProcessAdapter(getContext(),mList);
                    recyclerView.setAdapter(transactionProcessAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    diprosesIsEmpty.setVisibility(View.VISIBLE);
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

    private void callApiMenunggu(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetPesananModel> call = api.getPesananMenunggu(id_konsumen, token_konsumen);
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
                        mListMenunggu.add(new PesananModel(pesananModel.getId_order(), pesananModel.getSubtotal_harga(), pesananModel.getStatus(), pesananModel.getMetode_kirim(),mDataProduk, pesananModel.getNama_kios(), pesananModel.getAlamat_konsumen(), pesananModel.getNo_hp(), pesananModel.getBiaya_kirim()));
                    }
                    Log.d(TAG, "Data Menunggu" + mListMenunggu);

                    progressBar.setVisibility(View.GONE);
                    menungguIsEmpty.setVisibility(View.GONE);
                    diprosesIsEmpty.setVisibility(View.GONE);
                    TransactionProcessMenungguAdapter transactionProcessMenungguAdapter = new TransactionProcessMenungguAdapter(getContext(),mListMenunggu);
                    recyclerViewMenunggu.setAdapter(transactionProcessMenungguAdapter);
                    recyclerViewMenunggu.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    progressBar.setVisibility(View.GONE);
                    menungguIsEmpty.setVisibility(View.VISIBLE);
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

//    private void callApiDiantar(){
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
//        id_konsumen = sharedPreferences.getString("id_konsumen", null);
//        token_konsumen = sharedPreferences.getString("token", null);
//        Call<GetPesananModel> call = api.getPesananDiproses(id_konsumen, token_konsumen);
//        call.enqueue(new Callback<GetPesananModel>() {
//            @Override
//            public void onResponse(Call<GetPesananModel> call, Response<GetPesananModel> response) {
//
//
//                if (response.body().getResult() != null){
//                    List<PesananModel> list = response.body().getResult();
//                    Log.d(TAG, "Code :" + response.body().getResult());
//                    for (PesananModel pesananModel : list){
//                        List<ProductDetailPesananModel> listProduk = pesananModel.getProduk();
//                        mDataProduk = new ArrayList<>();
//                        for(ProductDetailPesananModel productDetailPesananModel : listProduk){
//                            mDataProduk.add(new ProductDetailPesananModel(productDetailPesananModel.getMerk(),productDetailPesananModel.getNama_produk()));
//                        }
//                        mList.add(new PesananModel(pesananModel.getId_order(), pesananModel.getSubtotal_harga(), pesananModel.getStatus(), pesananModel.getMetode_kirim(),mDataProduk, pesananModel.getNama_kios(), pesananModel.getAlamat_konsumen()));
//                    }
//                    Log.d(TAG, "Data " + mList);
//
//                    TransactionProcessAdapter transactionProcessAdapter = new TransactionProcessAdapter(getContext(),mList);
//                    recyclerView.setAdapter(transactionProcessAdapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                } else {
//                    Log.d(TAG, "Code :" + response.body().getMessage());
//                    Log.d(TAG, "Code :" + id_konsumen);
//                    Log.d(TAG, "Code :" + token_konsumen);
//                    return;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetPesananModel> call, Throwable t) {
//
//            }
//        });
//    }
}
