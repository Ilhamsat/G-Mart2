package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.HelperKeranjang;
import com.gmartdev.komsi.g_mart.Class.Nilai;
import com.gmartdev.komsi.g_mart.Model.DetailPesananKeranjangModel;
import com.gmartdev.komsi.g_mart.Model.ProductPesananDetailModel;
import com.gmartdev.komsi.g_mart.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailPesananKeranjangAdapter extends RecyclerView.Adapter<DetailPesananKeranjangAdapter.ViewHolderDetailPesananKeranjang> {

    Context mContext;
    List<ProductPesananDetailModel> mData;

    int[] jumlahPesan;

    HelperKeranjang helperKeranjang;
    List<Nilai> mNilai = new ArrayList<>();

    public DetailPesananKeranjangAdapter(Context mContext, List<ProductPesananDetailModel> mData, HelperKeranjang helperKeranjang){
        this.mContext = mContext;
        this.mData = mData;
        this.helperKeranjang = helperKeranjang;
    }


    @NonNull
    @Override
    public ViewHolderDetailPesananKeranjang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_detail_pesanan_keranjang,parent,false);
        ViewHolderDetailPesananKeranjang viewHolderDetailPesananKeranjang = new ViewHolderDetailPesananKeranjang(v);

        jumlahPesan = new int[mData.size()];
        for (int i = 0; i < mData.size(); i++){
            jumlahPesan[i] = Integer.parseInt(mData.get(i).getJumlah_pesan());
        }

        return viewHolderDetailPesananKeranjang;
    }

    public int totalPrice(int position){
        return Integer.parseInt(mData.get(position).getJumlah_pesan()) * Integer.parseInt(mData.get(position).getHarga());
    }

    public int totalPriceUpdate(int position){
        return jumlahPesan[position] * Integer.parseInt(mData.get(position).getHarga());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDetailPesananKeranjang holder, int position) {
        mNilai.add(new Nilai(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan[position]), mData.get(position).getHarga()));
        helperKeranjang.setValues(mNilai);
        holder.itemName.setText(mData.get(position).getMerk() + " " + mData.get(position).getNama_produk());
        holder.itemPrice.setText(mData.get(position).getHarga());
        holder.totalItem.setText(mData.get(position).getJumlah_pesan());
        holder.totalPrice.setText(String.valueOf(totalPrice(position)));
        holder.jumlahPesan.setText(mData.get(position).getJumlah_pesan());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahPesan[position] ++;
                holder.jumlahPesan.setText(String.valueOf(jumlahPesan[position]));
                holder.totalItem.setText(String.valueOf(jumlahPesan[position]));
                holder.totalPrice.setText(String.valueOf(totalPriceUpdate(position)));
                mNilai.get(position).setJumlah_pesan(String.valueOf(jumlahPesan[position]));
            }
        });
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlahPesan[position] == 0){
                    jumlahPesan[position] = 0;
                    holder.jumlahPesan.setText(String.valueOf(jumlahPesan[position]));
                    holder.totalItem.setText(String.valueOf(jumlahPesan[position]));
                    holder.totalPrice.setText(String.valueOf(totalPriceUpdate(position)));
                    mNilai.get(position).setJumlah_pesan(String.valueOf(jumlahPesan[position]));
                }else {
                    jumlahPesan[position] --;
                    holder.jumlahPesan.setText(String.valueOf(jumlahPesan[position]));
                    holder.totalItem.setText(String.valueOf(jumlahPesan[position]));
                    holder.totalPrice.setText(String.valueOf(totalPriceUpdate(position)));
                    mNilai.get(position).setJumlah_pesan(String.valueOf(jumlahPesan[position]));
                }
            }
        });

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
        private TextView jumlahPesan;
        private ImageButton reduce;
        private ImageButton add;

        public ViewHolderDetailPesananKeranjang(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemDetailPesananKeranjang);
            itemPrice = itemView.findViewById(R.id.priceItemDetailPesananKeranjang);
            totalItem = itemView.findViewById(R.id.jumlahDetailPesananKeranjang);
            totalPrice = itemView.findViewById(R.id.textTotalPriceDetailPesananKeranjang);
            jumlahPesan = itemView.findViewById(R.id.textNumberOrders);
            reduce = itemView.findViewById(R.id.reduce);
            add = itemView.findViewById(R.id.add);
        }
    }
}
