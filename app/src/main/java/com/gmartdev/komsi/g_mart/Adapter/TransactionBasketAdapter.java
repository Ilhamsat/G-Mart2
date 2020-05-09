package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.DetailPesananKeranjangActivity;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionBasketAdapter extends RecyclerView.Adapter<TransactionBasketAdapter.ViewHolderTransactionBasket> {

    Context mContext;
    List<PesananModel> mData;



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
    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransactionBasket holder, int position) {
        holder.howMany.setText(String.valueOf(mData.get(position).getProduk().size()));

        if(minus(position) == 0){
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk());
        }else {
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk() + " dan " + minus(position) + " barang lainnya");
        }
        holder.totalPriceItems.setText(mData.get(position).getSubtotal_harga());
        holder.delivery.setText(mData.get(position).getMetode_kirim());
        holder.contentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPesananKeranjangActivity.class);
                intent.putExtra("id_order", mData.get(position).getId_order());
                intent.putExtra("nama_kios", mData.get(position).getNama_kios());
                intent.putExtra("alamat_konsumen", mData.get(position).getAlamat_konsumen());
                mContext.startActivity(intent);
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

        public ViewHolderTransactionBasket(@NonNull View itemView) {
            super(itemView);

            howMany = itemView.findViewById(R.id.textHowManyBasket);
            items = itemView.findViewById(R.id.itemsBasket);
            totalPriceItems = itemView.findViewById(R.id.totalPriceItemsBasket);
            delivery = itemView.findViewById(R.id.delivery);
            contentBasket = itemView.findViewById(R.id.contentBasket);
        }
    }
}
