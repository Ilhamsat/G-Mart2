package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.DetailPesananRiwayatModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailPesananRiwayatAdapter extends RecyclerView.Adapter<DetailPesananRiwayatAdapter.ViewHolderDetailPesananRiwayat> {


    Context mContext;
    List<DetailPesananRiwayatModel> mData;

    public DetailPesananRiwayatAdapter(Context mContext, List<DetailPesananRiwayatModel> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolderDetailPesananRiwayat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_detail_pesanan_riwayat,parent,false);
        ViewHolderDetailPesananRiwayat viewHolderDetailPesananRiwayat = new ViewHolderDetailPesananRiwayat(v);
        return viewHolderDetailPesananRiwayat;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetailPesananRiwayat holder, int position) {
        holder.itemName.setText(mData.get(position).getItemName());
        holder.itemPrice.setText(mData.get(position).getItemPrice());
        holder.totalItem.setText(mData.get(position).getTotalItem());
        holder.totalPrice.setText(mData.get(position).getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderDetailPesananRiwayat extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private TextView totalItem;
        private TextView totalPrice;

        public ViewHolderDetailPesananRiwayat(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemDetailPesananRiwayat);
            itemPrice = itemView.findViewById(R.id.priceItemDetailPesananRiwayat);
            totalItem = itemView.findViewById(R.id.jumlah);
            totalPrice = itemView.findViewById(R.id.textTotalPrice);

        }
    }
}
