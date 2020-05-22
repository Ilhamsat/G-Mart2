package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.API.API;
import com.gmartdev.komsi.g_mart.Class.DetailPesananKeranjangActivity;
import com.gmartdev.komsi.g_mart.Model.GetCartModel;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.here.android.mpa.mapping.MapModelObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransactionBasketAdapter extends RecyclerView.Adapter<TransactionBasketAdapter.ViewHolderTransactionBasket> {

    private static final String TAG = "TransaksiBasketAdapter";

    Context mContext;
    List<PesananModel> mData;

    String id_konsumen, token_konsumen;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://gmart.vokasidev.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);


    public TransactionBasketAdapter(Context mContext, List<PesananModel> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public ViewHolderTransactionBasket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_basket,parent,false);
        ViewHolderTransactionBasket viewHolderTransactionBasket = new ViewHolderTransactionBasket(v);
        return viewHolderTransactionBasket;
    }

    public int minus(int position){
        return mData.get(position).getProduk().size()-1;
    }

    private void deleteKeranjang(String id_keranjang){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserData", mContext.MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);
        Call<GetCartModel> call = api.deleteKeranjang(token_konsumen, id_konsumen, id_keranjang);
        call.enqueue(new Callback<GetCartModel>() {
            @Override
            public void onResponse(Call<GetCartModel> call, Response<GetCartModel> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang +" berhasil di batalkan");
                }else {
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang +" gagal di batalkan");
                }
            }

            @Override
            public void onFailure(Call<GetCartModel> call, Throwable t) {

            }
        });
    }

    private void createPesanan(String id_pembayaran, String id_pengiriman, String id_kios, String cart, String id_keranjang) throws JSONException {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserData", mContext.MODE_PRIVATE);
        id_konsumen = sharedPreferences.getString("id_konsumen", null);
        token_konsumen = sharedPreferences.getString("token", null);

        JSONArray jsonArray = new JSONArray(cart);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
        }

        Log.d(TAG, "createPesanan: cart = " + jsonArray);

        Call<GetCartModel> call = api.setNewOrder(id_pembayaran, id_pengiriman, id_kios, id_konsumen, jsonArray, token_konsumen);
        call.enqueue(new Callback<GetCartModel>() {
            @Override
            public void onResponse(Call<GetCartModel> call, Response<GetCartModel> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang + " berhasil di beli");
                    deleteKeranjang(id_keranjang);
                }else {
                    Log.d(TAG, "onResponse: Keranjang dengan id " + id_keranjang + "  gagal di beli");
                }
            }

            @Override
            public void onFailure(Call<GetCartModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransactionBasket holder, int position) {



        holder.howMany.setText(String.valueOf(mData.get(position).getProduk().size()));
        if(minus(position) == 0){
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk());
        }else {
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk() + " dan " + minus(position) + " barang lainnya");
        }
        holder.totalPriceItems.setText(mData.get(position).getTotal());
        holder.delivery.setText(mData.get(position).getMetode_kirim());
        holder.contentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPesananKeranjangActivity.class);
                intent.putExtra("id_order", mData.get(position).getId_order());
                intent.putExtra("id_keranjang", mData.get(position).getId_keranjang());
                intent.putExtra("nama_kios", mData.get(position).getNama_kios());
                intent.putExtra("alamat_konsumen", mData.get(position).getAlamat_konsumen());
                intent.putExtra("total_harga", mData.get(position).getTotal());
                mContext.startActivity(intent);
            }
        });
        holder.buttonBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPesanan(mData.get(position).getId_pembayaran(), mData.get(position).getId_pengiriman(), mData.get(position).getId_kios(), mData.get(position).getCart(), mData.get(position).getId_keranjang());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.buttonBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteKeranjang(mData.get(position).getId_keranjang());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderTransactionBasket extends RecyclerView.ViewHolder {

        private TextView howMany;
        private TextView items;
        private TextView totalPriceItems;
        private TextView delivery;
        private RelativeLayout contentBasket;
        private MaterialButton buttonBeli;
        private MaterialButton buttonBatal;

        public ViewHolderTransactionBasket(@NonNull View itemView) {
            super(itemView);

            howMany = itemView.findViewById(R.id.textHowManyBasket);
            items = itemView.findViewById(R.id.itemsBasket);
            totalPriceItems = itemView.findViewById(R.id.totalPriceItemsBasket);
            delivery = itemView.findViewById(R.id.delivery);
            contentBasket = itemView.findViewById(R.id.contentBasket);
            buttonBeli = itemView.findViewById(R.id.buttonBeli);
            buttonBatal = itemView.findViewById(R.id.buttonBatalkan);

        }
    }
}
