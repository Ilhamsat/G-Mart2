package com.gmartdev.komsi.g_mart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmartdev.komsi.g_mart.Class.HelperKeranjang;
import com.gmartdev.komsi.g_mart.Class.Nilai;
import com.gmartdev.komsi.g_mart.Model.CartModel;
import com.gmartdev.komsi.g_mart.Model.ProductModel;
import com.gmartdev.komsi.g_mart.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAtStoreAdapter extends RecyclerView.Adapter<ItemAtStoreAdapter.ViewHolderListItemAtStore>{

    Boolean isAdded = false;

    Context mContext;
    List<ProductModel> mData;
    int jumlahPesan = 0;

    HelperKeranjang helperKeranjang;

    List<Nilai> mNilai = new ArrayList<>();
    List<CartModel> mCart = new ArrayList<>();

    public ItemAtStoreAdapter() {
    }

    public ItemAtStoreAdapter(Context mContext, List<ProductModel> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

//    public ItemAtStoreAdapter(Context mContext, List<ProductModel> mData, HelperKeranjang helperKeranjang){
//        this.mContext = mContext;
//        this.mData = mData;
//        this.helperKeranjang = helperKeranjang;
//    }

    @NonNull
    @Override
    public ViewHolderListItemAtStore onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.list_item_at_store,parent,false);
        ViewHolderListItemAtStore viewHolderListItemAtStore = new ViewHolderListItemAtStore(v);
        return viewHolderListItemAtStore;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListItemAtStore holder, int position) {
        holder.itemName.setText(mData.get(position).getMerk() + " " + mData.get(position).getNama_produk());
        holder.itemPrice.setText(mData.get(position).getHarga());

        holder.buttonSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.buttonSelectedItem.setVisibility(View.GONE);
                jumlahPesan = 1;
                holder.numberOrders.setText(String.valueOf(jumlahPesan));
                holder.contentNumber.setVisibility(View.VISIBLE);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlahPesan ++;
//                isAdded = true;
//                if(isAdded && jumlahPesan == 1){
//                    mNilai.add(new Nilai(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan), mData.get(position).getHarga()));
//                }else if (isAdded && jumlahPesan > 1) {
//                    mNilai.get(jumlahPesan).setJumlah(String.valueOf(jumlahPesan));
//                }

                holder.numberOrders.setText(String.valueOf(jumlahPesan));
//                mNilai.add(new Nilai());
//                helperKeranjang.setValues(mNilai);
//                if (jumlahPesan == 1){
//                    mCart.add(new CartModel(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan), mData.get(position).getHarga()));
//                }else if (jumlahPesan>1){
//                    mCart.remove(position);
//                    mCart.add(new CartModel(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan), mData.get(position).getHarga()));
//                }
            }
        });
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jumlahPesan == 1){
                    holder.buttonSelectedItem.setVisibility(View.VISIBLE);
                    holder.contentNumber.setVisibility(View.GONE);
//                    mCart.remove(position);
                } else {
                    jumlahPesan --;
                    holder.numberOrders.setText(String.valueOf(jumlahPesan));
//                    mCart.remove(position);
//                    mCart.add(new CartModel(mData.get(position).getId_produkkios(), String.valueOf(jumlahPesan), mData.get(position).getHarga()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String testMethod(int position){
        return mCart.get(position).getHarga();
    }


//    @Override
//    public List<Nilai> HelperKeranjang(String id_produkkios, String jumlah, String harga) {
//        List<Nilai> nilaiKerenjang = new ArrayList<>();
//        nilaiKerenjang = nilaiKerenjang.add(new Nilai(id_produkkios,jumlah,harga));
//        return nilaiKerenjang;
//    }
//
//    @Override
//    public int Size() {
//        return 0;
//    }

    public class ViewHolderListItemAtStore extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemPrice;
        private MaterialButton buttonSelectedItem;
        private RelativeLayout contentNumber;
        private ImageButton reduce;
        private ImageButton add;
        private TextView numberOrders;

        public ViewHolderListItemAtStore(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            buttonSelectedItem = itemView.findViewById(R.id.buttonSelectedAtStore);
            contentNumber = itemView.findViewById(R.id.contentNumber);
            reduce = itemView.findViewById(R.id.reduce);
            add = itemView.findViewById(R.id.add);
            numberOrders = itemView.findViewById(R.id.textNumberOrders);

        }
    }
}
