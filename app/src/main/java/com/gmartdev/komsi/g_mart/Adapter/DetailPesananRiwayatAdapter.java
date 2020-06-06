package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Model.DetailPesananRiwayatModel;
import com.gmartdev.komsi.g_mart.Model.ProductPesananDetailModel;
import com.gmartdev.komsi.g_mart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailPesananRiwayatAdapter extends RecyclerView.Adapter<DetailPesananRiwayatAdapter.ViewHolderDetailPesananRiwayat> {


    Context mContext;
    List<ProductPesananDetailModel> mData;

    public DetailPesananRiwayatAdapter(Context mContext, List<ProductPesananDetailModel> mData){
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

    public int totalPrice(int position){
        return Integer.parseInt(mData.get(position).getJumlah_pesan()) * Integer.parseInt(mData.get(position).getHarga());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetailPesananRiwayat holder, int position) {
        holder.itemName.setText(mData.get(position).getMerk() + " " + mData.get(position).getNama_produk());
        holder.itemPrice.setText(mData.get(position).getHarga());
        holder.totalItem.setText(mData.get(position).getJumlah_pesan());
        holder.totalPrice.setText(String.valueOf(totalPrice(position)));
        Picasso.get().load("http://gmart.vokasidev.com/api/images/produk/" + mData.get(position).getGambar()).into(holder.itemImage);
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
        private ImageView itemImage;

        public ViewHolderDetailPesananRiwayat(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemDetailPesananRiwayat);
            itemPrice = itemView.findViewById(R.id.priceItemDetailPesananRiwayat);
            totalItem = itemView.findViewById(R.id.jumlah);
            totalPrice = itemView.findViewById(R.id.textTotalPrice);
            itemImage = itemView.findViewById(R.id.imgItemDetailPesananRiwayat);

        }
    }
}
