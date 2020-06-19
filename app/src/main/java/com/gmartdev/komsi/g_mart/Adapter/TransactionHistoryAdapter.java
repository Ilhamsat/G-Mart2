package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.DetailPesananRiwayatActivity;
import com.gmartdev.komsi.g_mart.Model.PesananModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolderTransactionHistory> {

    Context mContext;
    List<PesananModel> mData;

    public TransactionHistoryAdapter(Context mContext, List<PesananModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolderTransactionHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_history,parent,false);
        ViewHolderTransactionHistory viewHolderTransactionHistory = new ViewHolderTransactionHistory(v);
        return viewHolderTransactionHistory;
    }

    public int minus(int position){
        return mData.get(position).getProduk().size()-1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTransactionHistory holder, int position) {
        if(minus(position) == 0){
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk());
        }else {
            holder.items.setText(mData.get(position).getProduk().get(0).getMerk() + " " + mData.get(position).getProduk().get(0).getNama_produk() + " dan " + minus(position) + " barang lainnya");
        }
        holder.totalPriceItems.setText(mData.get(position).getSubtotal_harga());
        holder.status.setText("Terkirim");
        holder.successOrNot.setImageResource(R.drawable.ic_success_white);

        holder.buttonRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailPesananRiwayatActivity.class);
//                intent.putExtra("storeName", mData.get(position).getStoreName());
//                intent.putExtra("storeDistance", mData.get(position).getDistance());
                intent.putExtra("id_order", mData.get(position).getId_order());
                intent.putExtra("id_kios", mData.get(position).getId_kios());
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

    public class ViewHolderTransactionHistory extends RecyclerView.ViewHolder {

        private TextView items;
        private TextView totalPriceItems;
        private TextView status;
        private ImageView successOrNot;
        private MaterialButton buttonRating;



        public ViewHolderTransactionHistory(@NonNull View itemView) {
            super(itemView);

            items = itemView.findViewById(R.id.items);
            totalPriceItems = itemView.findViewById(R.id.totalPriceItems);
            status = itemView.findViewById(R.id.status);
            successOrNot = itemView.findViewById(R.id.succesOrNot);

            buttonRating = itemView.findViewById(R.id.buttonSelectedAtStoreHistory);
        }
    }
}
