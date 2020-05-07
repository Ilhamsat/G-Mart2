package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.DetailPesananKeranjangModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailPesananKeranjangAdapter extends RecyclerView.Adapter<DetailPesananKeranjangAdapter.ViewHolderDetailPesananKeranjang> {

    Context mContext;
    List<DetailPesananKeranjangModel> mData;

    public DetailPesananKeranjangAdapter(Context mContext, List<DetailPesananKeranjangModel> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public ViewHolderDetailPesananKeranjang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_detail_pesanan_keranjang,parent,false);
        ViewHolderDetailPesananKeranjang viewHolderDetailPesananKeranjang = new ViewHolderDetailPesananKeranjang(v);
        return viewHolderDetailPesananKeranjang;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetailPesananKeranjang holder, int position) {
        holder.itemName.setText(mData.get(position).getItemName());
        holder.itemPrice.setText(mData.get(position).getItemPrice());
        holder.totalItem.setText(mData.get(position).getTotalItem());
        holder.totalPrice.setText(mData.get(position).getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderDetailPesananKeranjang extends RecyclerView.ViewHolder {

        private TextView itemName;
        private TextView itemPrice;
        private TextView totalItem;
        private TextView totalPrice;

        public ViewHolderDetailPesananKeranjang(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemDetailPesananKeranjang);
            itemPrice = itemView.findViewById(R.id.priceItemDetailPesananKeranjang);
            totalItem = itemView.findViewById(R.id.jumlahDetailPesananKeranjang);
            totalPrice = itemView.findViewById(R.id.textTotalPriceDetailPesananKeranjang);
        }
    }
}
